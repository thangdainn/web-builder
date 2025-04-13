package org.dainn.searchservice.config.endpoint;

public class Endpoint {
    public static final String API_PREFIX = "/api";

    public static final class User {
        public static final String BASE = API_PREFIX + "/users";
        public static final String ID = "/{id}";
        public static final String TEAM_MEMBER = "/subaccount/{id}";
    }
}
