CREATE TABLE medias
(
    id                      VARCHAR(36) PRIMARY KEY,
    type                    VARCHAR(255),
    name                    VARCHAR(255) NOT NULL,
    link                    TEXT        NOT NULL,
    sub_account_id          VARCHAR(36),
    created_at              DATETIME,
    updated_at              DATETIME
);