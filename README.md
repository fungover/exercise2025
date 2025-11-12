# 🏋️ Spring Boot Personal Best Tracker

A RESTful web application built with Spring Boot for tracking personal records (PRs) in fitness training.

## Tech Stack

- **Spring Boot** 3.5.6
- **Java** 25
- **Spring Data JPA** for database access
- **MySQL** 9.5.0
- **Spring Security** for authentication and authorization
- **Thymeleaf** for server-side rendering
- **Flyway** for database migrations
- **Docker Compose** for reproducible environment
- **JUnit 5** + **MockMvc** for testing

## Features

- ✅ User registration and authentication
- ✅ CRUD operations for exercises
- ✅ Personal record (PR) management
- ✅ Web-based UI with Thymeleaf
- ✅ RESTful API with JSON endpoints
- ✅ Input validation
- ✅ Secure access control

## Getting Started

### Prerequisites

- **Java 25** or higher
- **Maven** 3.6+
- **Docker** and **Docker Compose**
- **Git**

### Installation

1. **Create .env file**

   Create a `.env` file in the project root:
   ```env
   DATABASE_NAME=spring
   DATABASE_USER=user
   DATABASE_PASSWORD=secret
   DATABASE_ROOT_PASSWORD=verysecret
   ```

2. **Start the MySQL database**
   ```bash
   docker-compose up -d
   ```

   Wait ~10 seconds for MySQL to start (healthcheck runs automatically).

3. **Verify database is running**
   ```bash
   docker-compose ps
   ```
   You should see `mysql` with status `healthy`.

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   On first run, Flyway migrations will automatically create the database schema.

## Running Tests

Tests include:

- REST endpoint testing (GET, POST, DELETE)
- Spring Security access control
- Input validation
- Authenticated vs anonymous requests

## Web Interface

Navigate to http://localhost:8080 to:

- Register an account
- View and manage your personal records

## API Endpoints

### Public endpoints

| Method | Endpoint             | Description        |
|--------|----------------------|--------------------|
| `POST` | `/api/auth/register` | Register new user  |
| `GET`  | `/api/exercises`     | List all exercises |

### Protected endpoints (requires authentication)

| Method   | Endpoint       | Description                   |
|----------|----------------|-------------------------------|
| `GET`    | `/api/pr`      | Get my personal records       |
| `POST`   | `/api/pr`      | Create/update personal record |
| `DELETE` | `/api/pr/{id}` | Delete personal record        |



