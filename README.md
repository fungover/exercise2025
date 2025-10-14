![Java](https://img.shields.io/badge/Java-21-%23f89820?style=for-the-badge&logo=java&logoColor=white)
![Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-10-%23006fbf?style=for-the-badge&logo=jakartaee&logoColor=white)
![WildFly](https://img.shields.io/badge/WildFly-Server-%2348c9b0?style=for-the-badge&logo=wildfly&logoColor=white)

### 🐾 **Pet Store REST API**

 #### ➡️A Jakarta EE 10 RESTful service for managing virtual pets

🎯 **Overview**

**This project** is a simple Jakarta EE 10 REST API built to manage virtual pets.
You can adopt, feed, play with and release/delete pets.
All data is stored in-memory using a thread-safe ConcurrentHashMap (no database).
The project demonstrates dependency injection, validation, exception handling
and VG-level query parameters such as filtering, sorting and pagination.

🧱 **Tech Stack**
* Java 21
* Jakarta EE 10
* JAX-RS (REST API)
* CDI (Dependency Injection)
* Bean Validation
* WildFly (application server)
* PowerShell (automated testing)
* Maven

✨ **Features**

✅ Adopt, feed, play with, and release pets

✅ Input validation with Bean Validation (@Valid, @Min, @Max)

✅ Exception handling with custom mappers (@Provider)

✅ RESTful architecture using @Path, @GET, @POST, @PUT, @DELETE

✅ Filtering, sorting, and pagination (VG level)

✅ Automated PowerShell test script (test-pets.ps1)

🚀 **How to start the server**

This project runs a Jakarta EE 10 REST API inside WildFly.

From the project root, run: mvn clean package wildfly:run

Server will start locally at http://localhost:8080

The API bas URL is: http://localhost:8080/api

🧪 **Test Instructions**

You can test the REST API automatically using the PowerShell script.

👜 **Requirements**

* Windows PowerShell 5.1 or PowerShell 7+
* The server must be running locally (WildFly on http://localhost:8080)

🧪🤖 **How to Run the tests**

From the project root, run: cd test
.\test-pets.ps1

🏁 **Expected Outcome**

➡️The script runs a full sequence of API requests.

✅If everything is working correctly, you’ll see: ALL TESTS PASSED (11 ok)