# Pet API - Jakarta EE + Docker

Exercise 6 with RESTful API for managing virtual pets. Built with Jakarta EE, deployed on Wildfly and containerized with Docker.

___ 

## Getting started

### Requirements 
- Java 21 
- Maven 
- Docker desktop

### Build and run with WAR + wildfly 
mvn clean package
mvn wildfly:run / or run by configuration in IDE 

### Build and run with Docker
docker build -t exercise6-api .
docker run -p 8080:8080 exercise6-api

### Testing with curl or Bruno 
# Create a pet
curl -X POST http://localhost:8080/api/pets \
-H "Content-Type: application/json" \
-d '{"name":"Milo","species":"Dog","hungerLevel":20,"happinessLevel":80}'
# List all pets
curl http://localhost:8080/api/pets
# Feed or play with pet 
curl -X PUT http://localhost:8080/api/pets/1/feed - (or play)

### Technologies used 
- Jakarta EE(REST, validation)
- Wildfly
- Docker 
- Maven