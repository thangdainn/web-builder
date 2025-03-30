CREATE TABLE notifications
(
    id             VARCHAR(36) PRIMARY KEY,
    notification   TEXT NOT NULL,
    agency_id      VARCHAR(255),
    sub_account_id VARCHAR(255),
    user_id        VARCHAR(255),
    created_at     TIMESTAMP,
    updated_at    TIMESTAMP
);