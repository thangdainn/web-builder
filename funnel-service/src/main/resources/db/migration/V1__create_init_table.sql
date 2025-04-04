CREATE TABLE funnels
(
    id                      VARCHAR(36) PRIMARY KEY,
    name                    VARCHAR(255) NOT NULL,
    description             TEXT,
    current_period_end_date DATETIME,
    published               BOOLEAN      DEFAULT FALSE,
    subDomainName           VARCHAR(255) UNIQUE,
    favicon                 TEXT,
    sub_account_id          VARCHAR(36),
    live_products           VARCHAR(255),
    created_at              DATETIME,
    updated_at              DATETIME
);

CREATE TABLE class_names
(
    id          VARCHAR(36) PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    color       VARCHAR(255),
    custom_data LONGTEXT,
    funnel_id   VARCHAR(36),
    created_at  DATETIME,
    updated_at  DATETIME
);

CREATE TABLE funnel_pages
(
    id                VARCHAR(36) PRIMARY KEY,
    name              VARCHAR(255) NOT NULL,
    path_name         VARCHAR(255) NOT NULL DEFAULT '',
    visits            INTEGER      DEFAULT 0,
    content           LONGTEXT,
    funnel_page_order INTEGER,
    preview_image     TEXT,
    funnel_id         VARCHAR(36),
    created_at        DATETIME,
    updated_at        DATETIME
);

ALTER TABLE class_names
    ADD CONSTRAINT fk_funnel_id FOREIGN KEY (funnel_id) REFERENCES funnels (id);

ALTER TABLE funnel_pages
    ADD CONSTRAINT fk_funnel_p_id FOREIGN KEY (funnel_id) REFERENCES funnels (id);