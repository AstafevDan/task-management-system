--liquibase formatted sql

--changeset dan:1
CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY ,
    email VARCHAR(255) NOT NULL UNIQUE ,
    password_hash VARCHAR(255) NOT NULL ,
    name VARCHAR(64) NOT NULL ,
    role VARCHAR(32) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    last_modified_at TIMESTAMP DEFAULT NOW()
)
--rollback DROP TABLE users