package org.dainn.pipelineservice.config.endpoint;

public class Endpoint {
    public static final String API_PREFIX = "/api";

    public static final class Pipeline {
        public static final String BASE = API_PREFIX + "/pipelines";
        public static final String ID = "/{id}";
    }

    public static final class Lanes {
        public static final String BASE = API_PREFIX + "/lanes";
        public static final String ID = "/{id}";
    }

    public static final class Tickets {
        public static final String BASE = API_PREFIX + "/tickets";
        public static final String ID = "/{id}";
    }
}
