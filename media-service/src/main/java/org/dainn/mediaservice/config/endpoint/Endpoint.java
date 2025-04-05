package org.dainn.mediaservice.config.endpoint;

public class Endpoint {
    public static final String API_PREFIX = "/api";

    public static final class Media {
        public static final String BASE = API_PREFIX + "/medias";
        public static final String UPLOAD = "/upload";
        public static final String ID = "/{id}";
        public static final String SUB_ACCOUNT = "/sub-account/{id}";
    }
}
