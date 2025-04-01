package org.dainn.pipelineservice.config.endpoint;

public class Endpoint {
    public static final String API_PREFIX = "/api";

    public static final class Pipeline {
        public static final String BASE = API_PREFIX + "/pipelines";
        public static final String ID = "/{id}";
        public static final String LANE = "/{id}/lanes";
        public static final String SA = "/sa/{id}";
        public static final String LANE_ORDER = "/{id}/lane-order";
        public static final String TICKET_ORDER = "/{id}/ticket-order";
    }

    public static final class Lane {
        public static final String BASE = API_PREFIX + "/lanes";
        public static final String ID = "/{id}";
    }

    public static final class Ticket {
        public static final String BASE = API_PREFIX + "/tickets";
        public static final String ID = "/{id}";
    }

    public static final class Tag {
        public static final String BASE = API_PREFIX + "/tags";
        public static final String ID = "/{id}";
        public static final String SUB_ACCOUNT = "/sub-accounts/{id}";
    }
}
