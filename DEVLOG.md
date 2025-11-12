### Development Notes to enhance my own learning
_!important! I started this assignment in another branch (fagerdahl/exercise8), 
but it got messed up and required a fresh start. 
Therefore, I commit many things at once. I had to compare, copy and paste some things from my first branch._

## Assignment decomposed:
- I am supposed to build a webapp (a REST API + HTML) with Spring Boot.
- I will need a mini backend that can do CRUD operations with a DB.
- I will need to secure and test the API.

## Spring Boot
Spring Boot will:
- start a server for me (mvn spring-boot:run => server at http://localhost:8080)
- connect java classes, database, security and HTML
- It will also allow me to build an efficient and fast REST API, (JAVA EE needs a more complicated setup to do this)

## What I will create
- A REST API
- a database with my data of choise (I will gather famous movie quotes)
  Table/Entity = @Quotes
  Fields = id, quote, movie, character, year

- A Repository that speaks to the db (JpaRepository)
- A Controller that receives HTTP-requests (@RestController)

## What I will test
- API (ex: 200 OK)
- Security (ex: POST without login = unauthorized)

## TODO
[x] Start with docker - enable database

[x] Create entities/classes that will be the tables

[x] Do Repository and Controller

[] Test API endpoints with Postman

[] Add security layer

[] Add testing

[] Add Thymeleaf

## commands:
mvn spring-boot:run

## db = labb8db



