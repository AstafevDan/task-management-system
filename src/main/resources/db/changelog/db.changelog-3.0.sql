--liquibase formatted sql

--changeset dan:1
CREATE TABLE IF NOT EXISTS comments
(
    id BIGSERIAL PRIMARY KEY ,
    task_id BIGINT NOT NULL REFERENCES tasks (id),
    user_id BIGINT NOT NULL REFERENCES users (id),
    content TEXT NOT NULL ,
    created_at TIMESTAMP DEFAULT NOW() ,
    last_modified_at TIMESTAMP DEFAULT NOW()
)
--rollback DROP TABLE comments
