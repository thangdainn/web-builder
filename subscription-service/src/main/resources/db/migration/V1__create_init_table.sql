CREATE TABLE subscriptions
(
    id                      VARCHAR(36) PRIMARY KEY,
    plan                    VARCHAR(25),
    price                   DECIMAL(19, 2) NOT NULL,
    price_id                VARCHAR(36),
    current_period_end_date DATETIME,
    active                  BOOLEAN  DEFAULT TRUE,
    agency_id               VARCHAR(36) UNIQUE,
    subscription_id         VARCHAR(36),
    customer_id             VARCHAR(36),
    created_at              DATETIME,
    updated_at              DATETIME
);

CREATE TABLE addons
(
    id          VARCHAR(36) PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    price       DECIMAL(19, 2) NOT NULL,
    active      BOOLEAN  DEFAULT TRUE,
    agency_id   VARCHAR(36),
    created_at  DATETIME,
    updated_at  DATETIME
);