# Nexu Backend Coding Exercise

This is a Spring Boot application for managing car brands and models with their average prices.

## Prerequisites

- Java 17 JDK
- PostgreSQL or MySQL database
- Maven (included with Spring Boot)

## Setup Instructions

### 1. Database Configuration

#### Option A: PostgreSQL
1. Install PostgreSQL if you haven't already
2. Create an empty database named `test`
3. Update the database connection properties in `src/main/resources/application.properties`:


   `spring.datasource.url=jdbc:postgresql://localhost:5432/test
   spring.datasource.username=postgres
   spring.datasource.password=your_password`

#### Option A: MySQL
1. Install MySQL if you haven't already
2. Create an empty database named `test`
3. Update the database connection properties in `src/main/resources/application.properties`:


`spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect`


##The application will:
- Automatically create all required database tables on startup
- Populate the database with initial car brands and models
- Start on port 8081

##API Documentation
Once the application is running, you can access:

1. Swagger UI: http://localhost:8081/test/swagger-ui/index.html#/
2. API Docs: http://localhost:8081/test/v3/api-docs