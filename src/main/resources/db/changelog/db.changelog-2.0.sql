--liquibase formatted sql

--changeset dan:1
CREATE TABLE IF NOT EXISTS tasks
(
    id BIGSERIAL PRIMARY KEY ,
    title VARCHAR(64) NOT NULL ,
    description TEXT ,
    status VARCHAR(32) NOT NULL ,
    priority VARCHAR(32) NOT NULL ,
    author_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    executor_id BIGINT REFERENCES users (id) ,
    created_at TIMESTAMP DEFAULT NOW(),
    last_modified_at TIMESTAMP DEFAULT NOW()
)
--rollback DROP TABLE tasks