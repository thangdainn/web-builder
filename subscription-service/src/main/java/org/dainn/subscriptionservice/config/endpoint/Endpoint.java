package org.dainn.subscriptionservice.config.endpoint;

public class Endpoint {
    public static final String API_PREFIX = "/api";

    public static final class Subscription {
        public static final String BASE = API_PREFIX + "/subscriptions";
        public static final String ID = "/{id}";
    }

    public static final class AddOns {
        public static final String BASE = API_PREFIX + "/addons";
        public static final String ID = "/{id}";
    }
}
