## Bolåneradar Mini (Exercise 2)

Bolåneradar Mini is a simplified version of my upcoming full-scale project BolåneRadar.
It demonstrates the core backend structure — a RESTful Spring Boot application that displays banks and their average mortgage rates.

This mini version includes MySQL (via Docker), Spring Data JPA, Spring Security, and Thymeleaf for server-side rendering (SSR).

_______


### Tech Stack Overview
Technology | Purpose
------|-------
Spring Boot 3.5+ | Application framework
Spring Data JPA | ORM and database handling
MySQL 9 (Docker Compose) | Persistent database
Spring Security | Basic authentication
Thymeleaf | Server-side rendering (HTML)
JUnit + MockMvc | Integration and security testing


_______

### How to Run the Project

#### 1: Start MySQL via Docker
- docker compose up -d

#### 2: Run the Spring Boot Application
- mvn spring-boot:run

_______

## REST API Endpoints

### Bank Endpoints (/api/banks)

Method  |	Endpoint    |	Description
------|------|------
GET |	/api/banks  |	Retrieve all banks
POST    |	/api/banks  |	Add a new bank
DELETE  |	/api/banks  |	Delete all banks
POST    |	/api/banks/load-example-banks   |	Load example bank data

### Average Rate Endpoints (/api/rates)

Method  |	Endpoint    |	Description
------|------|------
GET |	/api/rates  |	Retrieve all average rates
POST    |	/api/rates  |	Add a new average rate
DELETE  |	/api/rates  |	Delete all rates
POST    |	/api/rates/load-example-data    |	Load example mortgage rate data for all banks

### Thymeleaf Page (/banks)

Method  |   Endpoint    |   Description
------|------|------
GET |	/banks  |	Displays all banks and their average rates in a table (SSR)

_______

## Testing

This project includes integration and access control tests for:
- AverageRateController
- BankController
- SecurityAccess

Run all tests:
- mvn test

_______

## Server-Side Rendering

The /banks endpoint renders an HTML page listing all banks and their average rates.
Styling is handled via /static/css/style.css.

_______

## Summary

- MySQL runs in Docker with persistent storage.
- REST endpoints secured with Basic Auth.
- Thymeleaf used for server-side rendering.
- Integration tests implemented using MockMvc and JUnit.
- Represents a foundation for the future full-scale BolåneRadar project.

_______

