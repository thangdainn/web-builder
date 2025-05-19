CREATE TABLE subscriptions
(
    id                      VARCHAR(36) PRIMARY KEY,
    plan                    VARCHAR(30) NOT NULL,
    price                   DECIMAL(19, 2) NOT NULL,
    price_id                VARCHAR(36),
    current_period_end_date TIMESTAMP,
    active                  BOOLEAN  DEFAULT FALSE,
    agency_id               VARCHAR(36),
    subscription_id         VARCHAR(36) UNIQUE,
    customer_id             VARCHAR(36),
    created_at              TIMESTAMP,
    updated_at              TIMESTAMP
);

CREATE TABLE addons
(
    id          VARCHAR(36) PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    price_id    VARCHAR(36) UNIQUE ,
    active      BOOLEAN  DEFAULT FALSE,
    agency_id   VARCHAR(36),
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP
);