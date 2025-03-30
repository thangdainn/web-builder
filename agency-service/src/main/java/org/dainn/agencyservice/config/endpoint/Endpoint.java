package org.dainn.agencyservice.config.endpoint;

public class Endpoint {
    public static final String API_PREFIX = "/api";

    public static final class Agency {
        public static final String BASE = API_PREFIX + "/agencies";
        public static final String ID = "/{id}";
    }

    public static final class AgencySO {
        public static final String BASE = API_PREFIX + "/agency-sos";
        public static final String ID = "/{id}";
    }
}
