package org.dainn.funnelservice.config.endpoint;

public class Endpoint {
    public static final String API_PREFIX = "/api";

    public static final class Funnel {
        public static final String BASE = API_PREFIX + "/funnels";
        public static final String ID = "/{id}";
    }

    public static final class FunnelPage {
        public static final String BASE = API_PREFIX + "/funnel-pages";
        public static final String ID = "/{id}";
    }

    public static final class ClassName {
        public static final String BASE = API_PREFIX + "/class-names";
        public static final String ID = "/{id}";
    }
}
