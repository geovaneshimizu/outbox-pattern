CREATE TABLE IF NOT EXISTS subscription_service.int_metadata_store (
  metadata_key   VARCHAR(255) NOT NULL,
  metadata_value VARCHAR(4000),
  region         VARCHAR(100) NOT NULL,

  CONSTRAINT int_metadata_store_pk PRIMARY KEY (metadata_key, region)
);
