--liquibase formatted sql

--changeset dan:1
CREATE TABLE IF NOT EXISTS tokens
(
    id SERIAL PRIMARY KEY ,
    token VARCHAR(255) NOT NULL UNIQUE ,
    token_type VARCHAR(255) NOT NULL ,
    is_expired BOOLEAN ,
    is_revoked BOOLEAN ,
    created_at TIMESTAMP DEFAULT NOW() ,
    last_modified_at TIMESTAMP DEFAULT NOW() ,
    user_id BIGINT NOT NULL REFERENCES users (id)
)
--rollback DROP TABLE tokens