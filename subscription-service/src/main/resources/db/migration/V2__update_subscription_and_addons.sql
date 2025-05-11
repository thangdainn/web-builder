ALTER TABLE addons
    ADD COLUMN price_id VARCHAR(36),
    ADD CONSTRAINT unique_price_id UNIQUE (price_id);

ALTER TABLE addons
    ALTER COLUMN active SET DEFAULT FALSE;

ALTER TABLE addons
    DROP COLUMN price;


ALTER TABLE subscriptions
    ADD CONSTRAINT unique_subscription_id UNIQUE (subscription_id);

ALTER TABLE subscriptions
    ALTER COLUMN active SET DEFAULT FALSE;