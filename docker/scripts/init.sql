DO
$$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_database
      WHERE datname = 'dbrpe'
   ) THEN
      PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE dbrpe');
   END IF;
END
$$;

\c dbrpe;

DROP TABLE IF EXISTS product;

CREATE TABLE product (
    product_id BIGSERIAL PRIMARY KEY,
    product_description VARCHAR(255) NOT NULL,
    product_status VARCHAR(10) CHECK (product_status IN ('ATIVO', 'CANCELADO')) NOT NULL,
    product_date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    product_date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);