INSERT INTO users (id, email, password_hash, name, role)
VALUES (1, 'admin@gmail.com', '12345678', 'Tom', 'ADMIN'),
        (2, 'user@gmail.com', '12345678', 'Brad', 'USER'),
        (3, 'test@gmail.com', '12345678', 'Luke', 'ADMIN');
SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO tasks (id, title, description, status, priority, author_id, executor_id)
VALUES (1, 'task1', 'description1', 'PENDING', 'LOW', 1, 2),
        (2, 'task2', 'description2', 'PENDING', 'MEDIUM', 3, 2),
        (3, 'task3', 'description3', 'IN_PROGRESS', 'LOW', 1, 3),
        (4, 'task4', 'description4', 'IN_PROGRESS', 'HIGH', 1, 2),
        (5, 'task5', 'description5', 'COMPLETED', 'MEDIUM', 3, 2);
SELECT SETVAL('tasks_id_seq', (SELECT MAX(id) FROM tasks));

INSERT INTO comments (id, task_id, user_id, content)
VALUES (1, 1, 1, 'Hello'),
        (2, 2, 2, 'Test'),
        (3, 3, 3, 'Test2');
SELECT SETVAL('comments_id_seq', (SELECT MAX(id) FROM comments));

INSERT INTO tokens (id, token, token_type, is_expired, is_revoked, user_id)
VALUES (1, '1234', 'BEARER', false, false, 1),
        (2, '1235', 'BEARER', false, false, 2),
        (3, '1233', 'BEARER', false, false, 3);
SELECT SETVAL('tokens_id_seq', (SELECT MAX(id) FROM tokens));