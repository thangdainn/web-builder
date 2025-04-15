package org.dainn.paymentservice.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;
import com.stripe.net.Webhook;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.SubscriptionCreateParams;
import com.stripe.param.SubscriptionUpdateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dainn.paymentservice.dto.*;
import org.dainn.paymentservice.dto.response.AgencyDto;
import org.dainn.paymentservice.dto.response.SubscriptionResp;
import org.dainn.paymentservice.event.EventProducer;
import org.dainn.paymentservice.exception.AppException;
import org.dainn.paymentservice.exception.ErrorCode;
import org.dainn.paymentservice.feignclient.IAgencyClient;
import org.dainn.paymentservice.service.IStripeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StripeService implements IStripeService {
    private final IAgencyClient agencyClient;
    private final EventProducer eventProducer;


    @Value("${stripe.secret-key}")
    private String secretKey;

    @Value("${platform.subscription.percent}")
    private double platformSubscriptionPercent;

    @Value("${platform.onetime.fee}")
    private long platformOneTimeFee;

    @Value("${platform.agency.percent}")
    private double platformAgencyPercent;

    @Value("${web-hook.secret-key}")
    private String whSecretKey;

    @Override
    public StripeResponse createSession(PaymentRequest request) {
        Stripe.apiKey = secretKey;
        boolean subscriptionPriceExists = request.getPrices().stream().anyMatch(PaymentRequest.Price::isRecurring);
        List<SessionCreateParams.LineItem> lineItems = request.getPrices().stream()
                .map(price -> SessionCreateParams.LineItem.builder()
                        .setPrice(price.getProductId())
                        .setQuantity(1L)
                        .build())
                .toList();

        SessionCreateParams.Builder builder = SessionCreateParams.builder()
                .setMode(subscriptionPriceExists ?
                        SessionCreateParams.Mode.SUBSCRIPTION :
                        SessionCreateParams.Mode.PAYMENT)
                .setUiMode(SessionCreateParams.UiMode.EMBEDDED)
                .setRedirectOnCompletion(SessionCreateParams.RedirectOnCompletion.NEVER)
                .addAllLineItem(lineItems);

        if (subscriptionPriceExists) {
            Map<String, String> metadata = new HashMap<>();
            metadata.put("connectAccountSubscriptions", "true");

            builder.setSubscriptionData(
                    SessionCreateParams.SubscriptionData.builder()
                            .putAllMetadata(metadata)
                            .setApplicationFeePercent(BigDecimal.valueOf(platformSubscriptionPercent))
                            .build()
            );
        } else {
            Map<String, String> metadata = new HashMap<>();
            metadata.put("connectAccountPayments", "true");

            builder.setPaymentIntentData(
                    SessionCreateParams.PaymentIntentData.builder()
                            .putAllMetadata(metadata)
                            .setApplicationFeeAmount(platformOneTimeFee * 100)
                            .build()
            );
        }

        Session session;
        try {
            session = Session.create(builder.build(),
                    RequestOptions.builder()
                            .setStripeAccount(request.getSubAccountConnectAccId())
                            .build());
        } catch (Exception e) {
            log.error("Error creating Stripe session: {}", e.getMessage());
            return StripeResponse.builder()
                    .status("error")
                    .message("Error creating Stripe session")
                    .build();
        }
        return StripeResponse.builder()
                .status("success")
                .message("Stripe session created successfully")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .clientSecret(session.getClientSecret())
                .build();
    }

    @Override
    public CreateCustomerResp createCustomer(CreateCustomer customer) {
        Stripe.apiKey = secretKey;
        Customer stripeCustomer;

        try {
            CustomerCreateParams params = CustomerCreateParams.builder()
                    .setName(customer.getName())
                    .setEmail(customer.getEmail())
                    .setPhone(customer.getPhone())
                    .setAddress(mapToStripeAddress(customer))
                    .setShipping(mapToStripeShipping(customer))
                    .build();
            stripeCustomer = com.stripe.model.Customer.create(params);
        } catch (Exception e) {
            log.error("Error creating Stripe customer: {}", e.getMessage());
            return CreateCustomerResp.builder()
                    .status("error")
                    .message("Error creating Stripe customer")
                    .build();
        }
        return CreateCustomerResp.builder()
                .status("success")
                .message("Stripe customer created successfully")
                .customerId(stripeCustomer.getId())
                .build();
    }

    @Override
    public SubscriptionResponse saveSubscription(SubscriptionRequest request) {
        Stripe.apiKey = secretKey;
        AgencyDto agency;
        try {
            ResponseEntity<AgencyDto> response = agencyClient.getByCustomerId(request.getCustomerId());
            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("Error fetching agency by customerId: {}", request.getCustomerId());
                throw new AppException(ErrorCode.USER_NOT_EXISTED);
            }
            agency = response.getBody();
        } catch (AppException e) {
            log.error("Agency not found for customerId: {}", request.getCustomerId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Agency not found for customerId: " + request.getCustomerId());
        } catch (Exception e) {
            log.error("Error fetching agency by customerId: {}", request.getCustomerId(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error fetching agency by customerId: " + e.getMessage());
        }

        assert agency != null;
        SubscriptionResp existingSubscription = agency.getSubscription();

        try {
            if (existingSubscription != null &&
                    existingSubscription.getActive() &&
                    existingSubscription.getSubscriptionId() != null) {

                log.info("Updating subscription for customerId: {}", request.getCustomerId());
                return updateSubscription(existingSubscription.getSubscriptionId(), request.getPriceId());
            } else {
                // Create new subscription
                log.info("Creating new subscription for customerId: {}", request.getCustomerId());
                return createSubscription(request.getCustomerId(), request.getPriceId());
            }
        } catch (StripeException e) {
            log.error("Stripe error: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error processing subscription: " + e.getMessage());
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Internal server error: " + e.getMessage());
        }
    }

    @Override
    public void handleWebhook(String payload, String sigHeader) {
        Set<String> STRIPE_WEBHOOK_EVENTS = Set.of(
                "product.created",
                "product.updated",
                "price.created",
                "price.updated",
                "checkout.session.completed",
                "customer.subscription.created",
                "customer.subscription.updated",
                "customer.subscription.deleted"
        );

        try {
            Event event = Webhook.constructEvent(payload, sigHeader, whSecretKey);

            if (STRIPE_WEBHOOK_EVENTS.contains(event.getType())) {
                EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
                StripeObject stripeObject = null;
                if (dataObjectDeserializer.getObject().isPresent()) {
                    stripeObject = dataObjectDeserializer.getObject().get();
                } else {
                    log.error("Failed to deserialize event data object.");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Failed to deserialize event data object.");
                }
                Subscription subscription = (Subscription) stripeObject;

                if (subscription.getMetadata() != null &&
                        !subscription.getMetadata().containsKey("connectAccountPayments") &&
                        !subscription.getMetadata().containsKey("connectAccountSubscriptions")) {

                    switch (event.getType()) {
                        case "customer.subscription.created":
                        case "customer.subscription.updated":
                            if ("active".equals(subscription.getStatus())) {
//                                subscriptionCreated(subscription, subscription.getCustomer());
                                eventProducer.sendPaymentProcessEvent(subscription, subscription.getCustomer());
                                log.info("CREATED FROM WEBHOOK {}", subscription.getId());
                            } else {
                                log.info("SKIPPED AT CREATED FROM WEBHOOK because subscription status is not active: {}",
                                        subscription.getId());
                            }
                            break;
                        default:
                            log.info("Unhandled relevant event! {}", event.getType());
                    }
                } else {
                    log.info("SKIPPED FROM WEBHOOK because subscription was from a connected account not for the application: {}",
                            subscription.getId());
                }
            }


        } catch (SignatureVerificationException e) {
            log.error("Webhook signature verification failed: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Webhook signature verification failed: " + e.getMessage());
        } catch (Exception e) {
            log.error("Webhook error: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Webhook error: " + e.getMessage());
        }
    }


    private SubscriptionResponse updateSubscription(String subscriptionId, String priceId) throws StripeException {
        // Retrieve current subscription to get item ID
        com.stripe.model.Subscription currentSubscription =
                com.stripe.model.Subscription.retrieve(subscriptionId);

        String currentItemId = currentSubscription.getItems().getData().get(0).getId();

        // Prepare items for update
        List<SubscriptionUpdateParams.Item> items = new ArrayList<>();

        // Mark current item as deleted
        items.add(SubscriptionUpdateParams.Item.builder()
                .setId(currentItemId)
                .setDeleted(true)
                .build()
        );

        // Add new price
        items.add(SubscriptionUpdateParams.Item.builder()
                .setPrice(priceId)
                .build()
        );

        // Update subscription
        SubscriptionUpdateParams params = SubscriptionUpdateParams.builder()
                .addAllItem(items)
                .addExpand("latest_invoice.payment_intent")
                .build();

        com.stripe.model.Subscription subscription =
                com.stripe.model.Subscription.retrieve(subscriptionId).update(params);

        // Get client secret from latest invoice's payment intent
        Invoice latestInvoice = subscription.getLatestInvoiceObject();
        PaymentIntent paymentIntent = latestInvoice.getPaymentIntentObject();

        return new SubscriptionResponse(subscription.getId(), paymentIntent.getClientSecret());
    }

    private SubscriptionResponse createSubscription(String customerId, String priceId) throws StripeException {
        // Prepare subscription items
        List<SubscriptionCreateParams.Item> items = new ArrayList<>();
        items.add(
                SubscriptionCreateParams.Item.builder()
                        .setPrice(priceId)
                        .build()
        );

        // Create subscription
        SubscriptionCreateParams params = SubscriptionCreateParams.builder()
                .setCustomer(customerId)
                .addAllItem(items)
                .setPaymentBehavior(SubscriptionCreateParams.PaymentBehavior.DEFAULT_INCOMPLETE)
                .setPaymentSettings(
                        SubscriptionCreateParams.PaymentSettings.builder()
                                .setSaveDefaultPaymentMethod(
                                        SubscriptionCreateParams.PaymentSettings.SaveDefaultPaymentMethod.ON_SUBSCRIPTION)
                                .build()
                )
                .addExpand("latest_invoice.payment_intent")
                .build();

        com.stripe.model.Subscription subscription = com.stripe.model.Subscription.create(params);

        // Get client secret from latest invoice's payment intent
        Invoice latestInvoice = subscription.getLatestInvoiceObject();
        PaymentIntent paymentIntent = latestInvoice.getPaymentIntentObject();

        // Save subscription in database
//        updateSubscriptionInDatabase(customerId, subscription.getId(), true, agency);

        return new SubscriptionResponse(subscription.getId(), paymentIntent.getClientSecret());
    }

    private CustomerCreateParams.Address mapToStripeAddress(CreateCustomer customer) {
        return CustomerCreateParams.Address.builder()
                .setLine1(customer.getLine1())
                .setCity(customer.getCity())
                .setState(customer.getState())
                .setPostalCode(customer.getZipCode())
                .setCountry(customer.getCountry())
                .build();
    }

    private CustomerCreateParams.Shipping mapToStripeShipping(CreateCustomer customer) {
        return CustomerCreateParams.Shipping.builder()
                .setName(customer.getName())
                .setPhone(customer.getPhone())
                .setAddress(CustomerCreateParams.Shipping.Address.builder()
                        .setLine1(customer.getLine1())
                        .setCity(customer.getCity())
                        .setState(customer.getState())
                        .setPostalCode(customer.getZipCode())
                        .setCountry(customer.getCountry())
                        .build())
                .build();
    }
}
