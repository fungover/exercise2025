# Famous Movie Quotes _Spring Boot Web Application_

A simple Spring Boot web application that demonstrates how to work with:
* REST API
* MySQL database (via Docker)
* JPA/Hibernate
* Thymeleaf HTML rendering
* Clean UI for displaying data nicely
* Basic Spring Boot configuration and structure

# How to Run the Application
1. Start MySQL in Docker
   docker-compose up -d

2. Run Spring Boot
   mvn spring-boot:run

3. Open the HTML page
   <http://localhost:8080/quotes>

4. Test the REST API
   GET http://localhost:8080/api/quotes

The app loads movie quotes from a database and displays them both in API- and webformat. 
### JSON = _http://localhost:8080/api/quotes_
### HTML + Thyme = _http://localhost:8080/quotes_

## Database (MySQL + Docker) 
The project uses MySQL running in Docker.

