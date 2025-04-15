package org.dainn.paymentservice.controller;


import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dainn.paymentservice.config.endpoint.Endpoint;
import org.dainn.paymentservice.dto.CreateCustomer;
import org.dainn.paymentservice.dto.PaymentRequest;
import org.dainn.paymentservice.dto.SubscriptionRequest;
import org.dainn.paymentservice.service.IStripeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Payment.BASE)
@RequiredArgsConstructor
public class PaymentController {
    private final IStripeService stripeService;

    @PostMapping(Endpoint.Payment.STRIPE)
    public ResponseEntity<?> checkoutProducts(@RequestBody PaymentRequest productRequest) {
        return ResponseEntity.ok(stripeService.createSession(productRequest));
    }

    @PostMapping(Endpoint.Payment.CREATE_CUSTOMER)
    public ResponseEntity<?> createCustomer(@RequestBody CreateCustomer customer) {
        return ResponseEntity.ok(stripeService.createCustomer(customer));
    }

    @PostMapping(Endpoint.Payment.SUBSCRIPTION)
    public ResponseEntity<?> createSubscription(@RequestBody SubscriptionRequest request) {
        return ResponseEntity.ok(stripeService.saveSubscription(request));
    }

    @PostMapping(Endpoint.Payment.WEBHOOK)
    public ResponseEntity<?> handleWebhook( @RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        stripeService.handleWebhook(payload, sigHeader);
        return ResponseEntity.ok().build();
    }
}
