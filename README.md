# Spring Boot Docker Demo

This project is a simple Spring Boot application that demonstrates how to use Docker and Docker Compose to run a Spring Boot application with a PostgreSQL database.

## Prerequisites

- Docker
- Docker Compose
- Java 21
- Maven

## Project Structure

- `src/main/java/com/demo/docker/controllers/TestController.java`: Contains the REST API endpoints.
- `docker-compose.yml`: Defines the Docker services for the application and PostgreSQL database.
- `Dockerfile`: Builds the Docker image for the Spring Boot application.

## Running the Application

1. **Build the Docker images:**

    ```sh
    docker-compose build
    ```

2. **Start the services:**

    ```sh
    docker-compose up
    ```

3. The Spring Boot application will be available at `http://localhost:8080`.

## API Endpoints

- **POST /**: Create a new test entity.
- **GET /**: Retrieve all test entities.
- **GET /{id}**: Retrieve a test entity by its ID.
- **DELETE /delete/{id}**: Delete a test entity by its ID.

## Configuration

The application uses the following environment variables for database configuration:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

These are set in the `docker-compose.yml` file.

## Building and Running Locally

To build and run the application locally without Docker:

1. **Build the project:**

    ```sh
    mvn clean package
    ```

2. **Run the application:**

    ```sh
    java -jar target/*.jar
    ```

## License

This project is licensed under the MIT License.