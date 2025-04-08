package org.dainn.funnelservice.config.endpoint;

public class Endpoint {
    public static final String API_PREFIX = "/api";

    public static final class Funnel {
        public static final String BASE = API_PREFIX + "/funnels";
        public static final String ID = "/{id}";
        public static final String DETAIL = "/{id}/detail";
        public static final String SUB_ACCOUNT = "/sub-accounts/{id}";
        public static final String PRODUCTS = "/{id}/products";
        public static final String DOMAIN = "/domain/{domain}";
    }

    public static final class FunnelPage {
        public static final String BASE = API_PREFIX + "/funnel-pages";
        public static final String ID = "/{id}";
        public static final String CONTENT = "/{id}/content";
        public static final String NAME = "/{id}/name";
    }

    public static final class ClassName {
        public static final String BASE = API_PREFIX + "/class-names";
        public static final String ID = "/{id}";
    }
}
