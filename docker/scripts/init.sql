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

DROP TABLE IF EXISTS Product;

CREATE TABLE Product (
    ProductId BIGINT PRIMARY KEY,
    ProductDescription VARCHAR(255) NOT NULL,
    ProductStatus VARCHAR(10) CHECK (ProductStatus IN ('ATIVO', 'CANCELADO')) NOT NULL,
    ProductDateCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ProductDateUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);