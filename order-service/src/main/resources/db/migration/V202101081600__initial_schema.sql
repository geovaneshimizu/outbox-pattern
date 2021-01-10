CREATE EXTENSION IF NOT EXISTS"uuid-ossp";

CREATE TABLE IF NOT EXISTS order_service.purchase_order (
    id         BIGSERIAL NOT NULL,
    public_id  UUID NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    user_email TEXT NOT NULL,
    event_id   BIGINT NOT NULL,
    sector_id  BIGINT NOT NULL,
    seat_id    BIGINT NOT NULL,

    CONSTRAINT order_pk PRIMARY KEY (id),
    UNIQUE (public_id)
);

CREATE TABLE IF NOT EXISTS order_service.outbox (
    id         BIGSERIAL NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    event      TEXT NOT NULL,
    payload    JSONB NOT NULL,

    CONSTRAINT outbox_pk PRIMARY KEY (id)
);
