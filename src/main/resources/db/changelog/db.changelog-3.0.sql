--liquibase formatted sql

--changeset dan:1
CREATE TABLE IF NOT EXISTS comments
(
    id BIGSERIAL PRIMARY KEY ,
    task_id BIGINT NOT NULL REFERENCES tasks (id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    content TEXT NOT NULL ,
    created_at TIMESTAMP DEFAULT NOW() ,
    last_modified_at TIMESTAMP DEFAULT NOW()
)
--rollback DROP TABLE comments
