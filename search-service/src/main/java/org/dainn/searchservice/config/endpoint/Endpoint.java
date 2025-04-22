package org.dainn.searchservice.config.endpoint;

public class Endpoint {
    public static final String API_PREFIX = "/api/search";

    public static final class User {
        public static final String BASE = API_PREFIX + "/users";
        public static final String ID = "/{id}";
        public static final String EMAIL = "/email/{email}";
        public static final String TEAM_MEMBER = "/members/{id}";
    }
}
