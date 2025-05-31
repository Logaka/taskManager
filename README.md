# Task Manager

## Описание

Task Manager — это REST API сервис для управления задачами с поддержкой регистрации, аутентификации пользователей (JWT, access/refresh flow) и CRUD-операций над задачами.  
Проект реализован на Java 21 с использованием Spring Boot 3, Spring Security, JPA (Hibernate), PostgreSQL.

---

## Основные возможности

- Регистрация и аутентификация пользователей
- Аутентификация через JWT (access/refresh токены)
- Защита приватных маршрутов (только для авторизованных пользователей)
- CRUD-операции над задачами
- Фильтрация задач по статусу (todo, in_progress, done)
- Роли пользователей (user, admin)
- Докеризация приложения и базы данных

---

## Технологии

- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA (Hibernate)
- PostgreSQL
- JWT (JSON Web Tokens)
- MapStruct
- Docker, Docker Compose

---

## Быстрый старт

### 1. Клонирование репозитория

```bash
git clone <repo-url>
cd taskManager
```

### 2. Настройка переменных окружения

Создайте файл `.env` в корне проекта и укажите:

```
POSTGRES_USER=your_db_user
POSTGRES_PASSWORD=your_db_password
POSTGRES_DB=your_db_name
HOST=localhost
JWT_SECRET=your_jwt_secret
```

### 3. Запуск через Docker Compose

```bash
docker-compose up --build
```

- Приложение будет доступно на `http://localhost:8080`
- База данных — на порту `5432`

---

## Основные эндпоинты

### Аутентификация

- `POST /api/auth/register` — регистрация пользователя
- `POST /api/auth/login` — вход, получение access/refresh токенов
- `POST /api/auth/refresh` — обновление access/refresh токенов

### Пользователи

- `GET /api/persons/{id}` — получить пользователя (только владелец или админ)
- `PUT /api/persons` — обновить пользователя
- `DELETE /api/persons/{id}` — удалить пользователя

### Задачи

- `POST /api/tasks/create` — создать задачу
- `GET /api/tasks/{id}` — получить задачу (только владелец/исполнитель)
- `PUT /api/tasks` — обновить задачу
- `DELETE /api/tasks/{id}` — удалить задачу

#### (Опционально) Фильтрация по статусу

- `GET /api/persons/{id}/tasks` — получить задачи пользователя  
  (фильтрация по статусу может быть добавлена через query-параметр, например: `?status=TODO`)

---

## Модели данных

### User (Person)

- `id`: Long
- `username`: String
- `password`: String (hash)
- `roles`: Set<Role>
- `createdTasks`: List<Task>
- `assignedTasks`: List<Task>

### Task

- `id`: Long
- `title`: String
- `description`: String
- `status`: [TODO, IN_PROGRESS, DONE]
- `createdAt`: LocalDateTime
- `updatedAt`: LocalDateTime
- `admin`: User
- `executor`: User

---

## Конфигурация

- Все параметры (БД, JWT) берутся из `.env` или `application.yaml`
- JWT:
  - `security.jwt.secret` — секрет для подписи токенов
  - `security.jwt.access` — срок жизни access-токена (часы)
  - `security.jwt.refresh` — срок жизни refresh-токена (дни)

---

## Тестирование

Для запуска тестов:

```bash
./mvnw test
```

---