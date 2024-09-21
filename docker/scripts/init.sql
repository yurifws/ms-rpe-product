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

DROP TABLE IF EXISTS client;

CREATE TABLE client (
    client_id BIGSERIAL PRIMARY KEY,
    client_name VARCHAR(255) NOT NULL,
    client_document VARCHAR(14) UNIQUE NOT NULL,
    client_status VARCHAR(10) CHECK (client_status IN ('ATIVO', 'BLOQUEADO', 'CANCELADO')) NOT NULL,
    client_date_of_birth DATE NOT NULL,
    client_date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    client_date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS card;

CREATE TABLE card (
    card_id BIGSERIAL PRIMARY KEY,
    card_number VARCHAR(20) NOT NULL,
    card_password VARCHAR(255) NOT NULL,
    card_status VARCHAR(10) CHECK (card_status IN ('ATIVO', 'BLOQUEADO', 'CANCELADO')) NOT NULL,
    client_id BIGSERIAL NOT NULL,
    card_holder_name VARCHAR(255) NOT NULL,
    product_id BIGSERIAL NOT NULL,
    card_date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    card_date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES client(client_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

DROP TABLE IF EXISTS user_auth;

CREATE TABLE user_auth (
	user_auth_id BIGSERIAL PRIMARY KEY,
    user_auth_login VARCHAR(255) NOT NULL,
    user_auth_password VARCHAR(255) NOT NULL
);

INSERT INTO user_auth (user_auth_login, user_auth_password) VALUES ('admin', '$2a$12$msuGths6lEKWVDbeFWpulOH1SinAeOmkGu6pV.oFrml.QstFq9PM.');