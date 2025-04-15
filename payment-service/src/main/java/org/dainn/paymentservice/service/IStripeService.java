package org.dainn.paymentservice.service;

import org.dainn.paymentservice.dto.*;

public interface IStripeService {
    StripeResponse createSession(PaymentRequest request);
    CreateCustomerResp createCustomer(CreateCustomer customer);
    SubscriptionResponse saveSubscription(SubscriptionRequest request);
    void handleWebhook(String payload, String sigHeader);
}
