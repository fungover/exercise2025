# Pet API

A simple Jakarta EE 10 application built with Java 21 and WildFly.  
It exposes a RESTful API for managing virtual pets in-memory.
___
## How to run

### Prerequisites
- Java 21 (Temurin / OpenJDK)
- Maven
- WildFly 35+

### Run the application
```
mvn clean package wildfly:run
```

### When the server has started the API will be available at:
```
http://localhost:8080/pet-api/api/pets
```
___

### API EndPoints

#### Create/adopt a new pet
```
POST /api/pets
Content-Type: application/json

{
  "name": "Fido",
  "species": "dog",
  "hungerLevel": 50,
  "happiness": 60
}
```

#### List pets
```
GET /api/pets
```

#### Query Parameters (optional)
- offset and limit -> pagination
- species -> filter by species
- sortBy -> name | species | hungerLevel | happiness
- order -> asc | desc
```
GET /api/pets?species=dog&sortBy=happiness&order=desc&offset=0&limit=5
```

#### Get pet by id
```
GET /api/pets/{id}
```

#### Feed a pet
```
POST /api/pets/{id}/feed
```
- Decreases the pet’s hungerLevel by 10 (minimum 0).

#### Play with a pet
```
PUT /api/pets/{id}/play
```
- Increases the pet’s happiness by 10 (maximum 100).

#### Delete/release a pet
```
DELETE /api/pets/{id}
```
___

### Validation & Errors
- Bean Validation is used (@NotBlank, @Min, @Max).
- Invalid input → 400 Bad Request with error messages in JSON.
- Non-existing resource → 404 Not Found.

___

### Extra (Testing)

- The API was manually tested with Bruno REST client to verify all endpoints.

___