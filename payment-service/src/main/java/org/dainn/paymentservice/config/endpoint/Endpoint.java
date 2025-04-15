package org.dainn.paymentservice.config.endpoint;

public class Endpoint {
    public static final String API_PREFIX = "/api";

    public static final class Payment {
        public static final String BASE = API_PREFIX + "/payments";
        public static final String STRIPE = "/stripe";
        public static final String CREATE_CUSTOMER = STRIPE + "/create-customer";
        public static final String SUBSCRIPTION = STRIPE + "/subscription";
        public static final String WEBHOOK = STRIPE + "/webhook";
    }
}
