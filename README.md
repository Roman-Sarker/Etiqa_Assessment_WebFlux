# Technical Project Document: Customer and Product Management System - Etiqa
## Project Overview
This backend API project is developed using **Java Spring WebFlux**, which implements reactive programming. The goal of the system is to manage customer and product information while handling purchases. All operations are non-blocking, making it highly scalable for modern web applications.

## Features

### 1. Customer Management
- **Create**: Add a new customer to the system.
- **Update**: Modify existing customer details.
- **Delete**: Remove a customer from the system.
- **Provide customer by customer ID**: Retrieve customer details based on their unique ID.
- **Get all customers**: Fetch all customer records from the database.
- **Provide customers by date range**: Fetch customers added between a given start and end date.

### 2. Product Management
- **Create**: Add a new product to the system.
- **Update**: Modify existing product details.
- **Delete**: Remove a product from the system.
- **Provide product by product ID**: Retrieve product details by its ID.
- **Get all products**: Fetch all product records from the database.

### 3. Purchase Management
- **Purchase a product by customer**: Record a customer purchasing a product.
- **Get all purchase history**: Fetch the complete purchase history within a date range.
- **Product stock reduction**: Upon a successful purchase, the quantity of the purchased product is reduced in the **Products** table.

## Technologies Used
1. **JDK**: Version 19
2. **Spring Boot**: Version 3.3.3
3. **WebFlux Reactive Programming**: Key to non-blocking, asynchronous data processing
4. **Maven Project**: For dependency and project management
5. **IDE**: IntelliJ IDEA 2014.1.4 (Community Edition)
6. **Database**: MySQL - 10.4.28-MariaDB for persistence
7. **Swagger UI**: Document and Test.

## Design Pattern
The project uses a Java skeleton based on the **Spring MVC** architecture, incorporating controller, service, and repository layers, with a focus on **reactive programming** and **non-blocking I/O**.

## Database Connection
MySQL is the database of choice, and connection properties are configured in the **application.properties** file.
- **Database URL**: jdbc:mysql://localhost:3306/customer_product_db
- **Username**: root
- **Password**: [masked for security]

## Maven Dependencies
1. **spring-boot-starter-webflux**: Supports reactive web application development.
2. **spring-boot-starter-data-r2dbc**: Provides R2DBC support for reactive database access.
3. **r2dbc-mysql**: Enables reactive MySQL communication.
4. **Lombok**: Reduces boilerplate code through annotations.
5. **spring-boot-starter-validation**: Provides validation support in the application.
6. **slf4j-api**: Logging abstraction for various logging frameworks.
7. **logback-classic**: The logging framework implementation used.
8. **springdoc-openapi-starter-webflux-ui**: For generating and exposing Swagger UI for API documentation.

## Global Exception Handling
- A centralized **Global Exception Handler** captures and handles all exceptions raised in the service layer.
- **Custom exception classes** are created to provide meaningful messages and HTTP statuses.
- Any unhandled exceptions will be caught by the global handler, which then returns a structured error response.


## Validation

This project uses the **jakarta.validation** package to ensure that the data provided to the system is correct and meets all necessary criteria. Validation annotations such as `@NotNull`, `@Size`, and `@Email` are used to enforce constraints on model classes. 

In case of validation errors, the **Global Exception Handler** captures these issues and provides a clear, informative response to the client.

### System Validations

#### 1. Customer Management

- **Before Save & Update:**
  1. First name cannot be blank.
  2. Last name cannot be blank.
  3. Verify the email address format using `@Email`.
  4. Status field cannot be empty and must have a maximum size of 1 character.

- **Get by ID and Date Range:**
  1. Returns an error if no customer is found with the specified ID (e.g., `Customer not found with id: '300'`).
  2. Returns an error if no customer is found within the specified date range (e.g., `Customer not found with date between 2024-09-17 and 2024-09-17`).

#### 2. Product Management

- **Before Save & Update:**
  1. Book title cannot be empty.
  2. Book price must be greater than 0.
  3. Book quantity must be greater than 0.
  4. Status field cannot be empty and must have a maximum size of 1 character.

- **Get by ID:**
  1. Returns an error if no product is found with the specified ID (e.g., `Product not found with id: '30'`).

#### 3. Purchase Management

- **Before Save:**
  1. Verify that the customer ID exists.
  2. Verify that the product ID exists.
  3. Check for sufficient product quantity before processing the purchase.

- **Get by Purchase ID and Date Range:**
  1. Returns an error if no purchase is found within the specified date range (e.g., `Purchase not found with date between 2024-09-17 and 2024-09-17`).


## Logging
Logging is implemented using **Logback** with support for SLF4J API.
1. **Application-level logging**: Logs key application events like startup, shutdown, and errors.
2. **Request and Response logging**: Logs all incoming HTTP requests and outgoing responses to ensure transparency and traceability.

## Database Structure
The application consists of three primary tables:
1. **CUSTOMERS**: Stores customer details (ID, first name, last name, etc.).
2. **PRODUCTS**: Stores product details (ID, book title, price, quantity, etc.).
3. **PURCHASES**: Stores purchase history (ID, customer ID, product ID, quantity, price).

### Database Triggers
1. **before_purchases_insert**: 
   - Automatically inserts the current date into the `created_date` column.
   - After a successful purchase, the trigger reduces the available quantity of the purchased product in the **Products** table.

2. **before_customer_insert**:
   - Automatically inserts the current date into the `created_date` column when a customer is added.

3. **before_product_insert**:
   - Automatically inserts the current date into the `created_date` column when a product is added.

## REST API Endpoints

### Customers
- **POST** `/customers`: Create a new customer.
- **GET** `/customers/{id}`: Retrieve a customer by their ID.
- **GET** `/customers`: Retrieve all customers.
- **GET** `/customers?startDate={startDate}&endDate={endDate}`: Retrieve customers added within a date range.
- **PUT** `/customers/{id}`: Update customer details.
- **DELETE** `/customers/{id}`: Delete a customer by ID.

### Products
- **POST** `/products`: Create a new product.
- **GET** `/products/{id}`: Retrieve a product by its ID.
- **GET** `/products`: Retrieve all products.
- **PUT** `/products/{id}`: Update product details.
- **DELETE** `/products/{id}`: Delete a product by ID.

### Purchases
- **POST** `/purchases`: Purchase a product (reduces product quantity).
- **GET** `/purchases`: Retrieve all purchase history.
- **GET** `/purchases?startDate={startDate}&endDate={endDate}`: Retrieve purchase history within a date range.

### Swagger UI
The project integrates Swagger UI to provide API documentation and testing tools. It enables developers and testers to visualize API endpoints, making it easier to understand and test API requests and responses.
***URL : http://localhost:8080/swagger-ui.html***

---

This document outlines the core features, technologies, and database architecture for the **Customer and Product Management System** built using **Java Spring WebFlux** with reactive programming concepts. The application provides scalable and efficient backend services with robust exception handling, validation, and logging mechanisms.
