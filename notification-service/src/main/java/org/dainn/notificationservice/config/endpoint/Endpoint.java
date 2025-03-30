package org.dainn.notificationservice.config.endpoint;

public class Endpoint {
    public static final String API_PREFIX = "/api";

    public static final class Notification {
        public static final String BASE = API_PREFIX + "/notifications";
        public static final String subAccountId = "/sub-account";
        public static final String userId = "/user";
    }
}
