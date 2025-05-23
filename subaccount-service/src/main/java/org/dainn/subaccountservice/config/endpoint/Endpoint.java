package org.dainn.subaccountservice.config.endpoint;

public class Endpoint {
    public static final String API_PREFIX = "/api";

    public static final class SubAccount {
        public static final String BASE = API_PREFIX + "/sub-accounts";
        public static final String ID = "/{id}";
        public static final String DETAIL = "/{id}/detail";
        public static final String CONNECT_ACC_ID = "/{id}/connect-acc-id";
        public static final String AGENCY = "/agency/{id}";
    }

    public static final class Contact {
        public static final String BASE = API_PREFIX + "/contacts";
        public static final String ID = "/{id}";
        public static final String SUB_ACCOUNT = "/sub-accounts/{id}";
    }
}
