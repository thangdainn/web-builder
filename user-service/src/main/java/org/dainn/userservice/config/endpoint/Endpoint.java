package org.dainn.userservice.config.endpoint;

public class Endpoint {
    public static final String API_PREFIX = "/api";

    public static final class User {
        public static final String BASE = API_PREFIX + "/users";
        public static final String ID = "/{id}";
        public static final String DETAIL = "/{id}/detail";
        public static final String EMAIL = "/{email}";
    }

    public static final class Permission {
        public static final String BASE = API_PREFIX + "/permissions";
        public static final String USER = "/user/{id}";
        public static final String ACCESS = "/{id}/access";
    }

    public static final class Invitation {
        public static final String BASE = API_PREFIX + "/invitations";
        public static final String AGENCY = "/agency/{id}";
        public static final String PENDING = "/pending/{email}";
        public static final String VERIFY = "/{email}/verify";
    }
}
