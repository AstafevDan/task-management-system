# Task Management System

## Запуск
```bash
# Клонировать репозиторий
git clone https://github.com/AstafevDan/task-management-system.git

# Перейти в свою директорию
cd your-project
```

Потом переименуйте файл **.env-example** в **.env**. Установите в нём свои значения для имени пользователя, пароля и url базы данных.
Далее с помощью Docker:
```bash
#Сборка, создание и запуск контейнеров
docker compose up -d
```

Ознакомиться с API можно по ссылке в браузере: http://localhost:8081/swagger-ui/index.html

## Технологии

  - Java 17
  - Spring Boot 3.3.6
  - PostgreSQL 16.3
  - Docker
