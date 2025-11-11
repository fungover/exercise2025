# 📦 Exercise 8 – Inventory Management with Spring Boot

This project is part of a Java programming course and implements a simple inventory system using Spring Boot, MySQL, and Thymeleaf.

## 🚀 Features

- View all products and their storage locations
- Add new products via HTML form
- Delete products from the list
- REST API for products (`GET`, `POST`)
- Separate controllers for REST and view
- Basic authentication with Spring Security
- Unit tests for REST and security

## 🧱 Technologies Used

| Technology        | Purpose                      |
|-------------------|------------------------------|
| Spring Boot 3.5   | Backend and configuration    |
| Spring Data JPA   | Database access              |
| Spring Security   | Form authentication          |
| Thymeleaf         | HTML rendering               |
| MySQL 9           | Database (via Docker)        |
| Adminer           | DB management (via Docker)   |
| JUnit 5 + Mockito | Testing                      |

## 🐳 Run with Docker
(Docker desktop open)

```bash
docker-compose up
```
This starts 
- MySQL on port 3306 
- Adminer on port 8081

---

## ▶️ Run the Application

```bash
mvn spring-boot:run
```
Then visit http://localhost:8080
 to use the Thymeleaf HTML interface.


## 🔐 Authentication (for API)

REST endpoints like /products require HTTP Basic Auth:
Username: admin  
Password: admin

🧪 Run Tests

Run all tests with:
```bash
mvn test
```
Includes:

ProductControllerTest – tests GET and POST endpoints

SecurityTest – verifies 401 Unauthorized for unauthenticated access

