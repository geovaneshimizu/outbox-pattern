CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS subscription_service.subscription (
  id                BIGSERIAL NOT NULL,
  created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  purchase_order_id UUID NOT NULL,
  expires_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  user_email        TEXT NOT NULL,
  sector_id         BIGINT NOT NULL,
  seat_id           BIGINT NOT NULL,

  CONSTRAINT subscription_pk PRIMARY KEY (id)
);

CREATE INDEX subscription_purchase_order_id_idx
  ON subscription_service.subscription
  USING hash (purchase_order_id);
