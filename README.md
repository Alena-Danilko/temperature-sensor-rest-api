# Temperature Sensor REST API
The project is a REST API service for working with a sensor for measuring ambient air temperature and detecting the presence of rain. The project also includes a Client part for sending data to the REST API.

## Features:
- REST API service for receiving and sending data.
- Measuring ambient air temperature.
- Detecting rain.
- Client application for sending data to the server.

## Technologies:
- Java 8+
- Maven
- PostgreSQL
- Spring Boot
- Hibernate
- JUnit 5

## Installation
1. Clone the repository: git clone [https://github.com/Alena-Danilko/temperature-sensor-rest-api.git](https://github.com/Alena-Danilko/temperature-sensor-rest-api.git)

2. Navigate to the project directory: cd temperature-sensor-rest-api

3. Configure the database connection in src/main/java/com/RestApiProject/target/classes/application.properties.:
- spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
- spring.datasource.username=your_username
- spring.datasource.password=your_password

4. Build the project: mvn clean install

## Usage
1. Run the application: mvn spring-boot:run
2. The API will be available at http://localhost:8080
   
## API Endpoints
GET /measurements - Get all measurements from the database.

GET /measurements//rainyDaysCount - Get the number of rainy days from the database.

POST /measurements/add - Send data from the sensor to the server.

POST /sensors/registration - Register a new sensor in the system.


## Project structure
## Main code
src/main/java/com/RestApiProject - Server source code.

src/main/java/com/RestApiProject/controllers - REST controllers.

src/main/java/com/RestApiProject/dto - Data Transfer Object (DTO).

src/main/java/com/RestApiProject/models - Data Models.

src/main/java/com/RestApiProject/repositories - Repositories for working with the database.

src/main/java/com/RestApiProject/service - Business logic of the app.

src/main/java/com/RestApiProject/util - Helper classes for exception handling and validation.

src/main/java/com/RestApiProject/target/classes/application.properties - Configuration file.

src/main/java - Client source code.

src/main/java/dto - Data Transfer Object for Client.

## Testing
src/test/java/com/RestApiProject - Tests for the server source code.

src/main/java/com/RestApiProject/controllersTest - Tests for REST controllers.

src/main/java/com/RestApiProject/dtoTest - Tests for Data Transfer Object.

src/main/java/com/RestApiProject/modelsTest - Tests for Data Models.

src/main/java/com/RestApiProject/repositoriesTest - Tests for repositories.

src/main/java/com/RestApiProject/serviceTest - Tests for business logic.

src/main/java/com/RestApiProject/utilTest - Tests for helper classes for exception handling and validation.

src/test/java - Tests for client source code.


