CREATE EXTENSION IF NOT EXISTS"uuid-ossp";

CREATE TABLE IF NOT EXISTS order_service.purchase_order (
  id          BIGSERIAL NOT NULL,
  external_id UUID NOT NULL DEFAULT uuid_generate_v4(),
  created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  user_email  TEXT NOT NULL,
  event_id    BIGINT NOT NULL,
  sector_id   BIGINT NOT NULL,
  seat_id     BIGINT NOT NULL,

  CONSTRAINT purchase_order_pk PRIMARY KEY (id)
);

CREATE INDEX purchase_order_external_id_idx
  ON order_service.purchase_order
  USING hash (external_id);

CREATE TABLE IF NOT EXISTS order_service.outbox (
  id         BIGSERIAL NOT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  event      TEXT NOT NULL,
  payload    JSONB NOT NULL,

  CONSTRAINT outbox_pk PRIMARY KEY (id)
);
