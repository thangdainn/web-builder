CREATE TABLE subscriptions
(
    id                      VARCHAR(36) PRIMARY KEY,
    plant                   VARCHAR(255)   NOT NULL,
    price                   DECIMAL(19, 2) NOT NULL,
    current_period_end_date DATETIME,
    active                  BOOLEAN  DEFAULT TRUE,
    agency_id               VARCHAR(36),
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