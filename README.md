## How to run the application

* Ensure Docker is running (Docker Desktop or daemon)
* Run application
* The application will be available at `http://localhost:8080`

_Before starting Exercise2025AuthApplication, make sure Docker is running._

⚠️ Important Note on First Launch

On the first launch, the database within the Docker container might start slower than the application itself. Because of this, the application may throw a connection error related to the database.

In this case:

    Check and, if necessary, stop or remove any old Docker containers for the database that might be using the same ports or name as the current application configuration.

    Run the application again.

### Dev profile
By default, the application uses the dev profile, which configures the MySQL database connection, initializes seed data, and enables debug logging. See `application-dev.properties` for details.