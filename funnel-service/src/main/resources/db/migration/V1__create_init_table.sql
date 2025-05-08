CREATE TABLE funnels
(
    id                      VARCHAR(36) PRIMARY KEY,
    name                    VARCHAR(255) NOT NULL,
    description             TEXT,
    published               BOOLEAN      DEFAULT FALSE,
    sub_domain_name         VARCHAR(255) UNIQUE,
    favicon                 TEXT,
    sub_account_id          VARCHAR(36),
    live_products           VARCHAR(255),
    created_at              TIMESTAMP,
    updated_at              TIMESTAMP
);

CREATE TABLE class_names
(
    id          VARCHAR(36) PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    color       VARCHAR(255),
    custom_data TEXT,
    funnel_id   VARCHAR(36),
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP
);

CREATE TABLE funnel_pages
(
    id                VARCHAR(36) PRIMARY KEY,
    name              VARCHAR(255) NOT NULL,
    path_name         VARCHAR(255) NOT NULL DEFAULT '',
    visits            INTEGER      DEFAULT 0,
    content           TEXT,
    funnel_page_order INTEGER,
    preview_image     TEXT,
    funnel_id         VARCHAR(36),
    created_at        TIMESTAMP,
    updated_at        TIMESTAMP
);

ALTER TABLE class_names
    ADD CONSTRAINT fk_funnel_id
        FOREIGN KEY (funnel_id)
            REFERENCES funnels (id)
            ON DELETE CASCADE;

ALTER TABLE funnel_pages
    ADD CONSTRAINT fk_funnel_p_id
        FOREIGN KEY (funnel_id)
            REFERENCES funnels (id)
            ON DELETE CASCADE;