# Library Management System

## About the Project

This project implements a Library Management System using Spring MVC. It includes controllers for managing teachers and books, providing various endpoints for CRUD operations and additional functionalities. All of the endpoints are tested with Postman and project is ready to use after DB configuration.

### Teacher Controller

The `TeacherController` manages teacher-related operations:

- **Save Teacher**: `POST /teachers`
- **Get All Teachers**: `GET /teachers`
- **Find Teacher by ID (PathVariable)**: `GET /teachers/{id}`
- **Find Teacher by ID (RequestParam)**: `GET /teachers/query?id={id}`
- **Delete Teacher by ID**: `DELETE /teachers/{id}`
- **Update Teacher by ID**: `PUT /teachers/{teacherId}`
- **Get Teacher by DTO (RequestParam)**: `GET /teachers/query/dto?id={id}`
- **Get Teachers with Pagination**: `GET /teachers/page?page={page}&size={size}&sort={sort}&direction={direction}`
- **Add Teacher for a Book**: `POST /teachers/{bookId}/teachers`

### Book Controller

The `BookController` manages book-related operations:

- **Save Book**: `POST /books`
- **Get All Books**: `GET /books`
- **Get Book by ID**: `GET /books/{id}`
- **Get Book by ID (RequestParam)**: `GET /books/query?id={id}`
- **Get Books by Title**: `GET /books/title?title={title}`
- **Delete Book by ID**: `DELETE /books/{id}`
- **Update Book by ID**: `PUT /books/{bookId}`
- **Get Book with DTO (RequestParam)**: `GET /books/query/dto?id={id}`
- **Add Book for a Teacher**: `POST /books/{teacherId}/books`
- **Get Books with Pagination**: `GET /books/page?p={page}&size={size}&sort={sort}&direction={direction}`

## Usage

1. Clone the repository.
2. Configure your database settings in the `application.properties` file.
3. Run the application using your preferred method (e.g., IDE or `mvn spring-boot:run`).
4. Access the provided endpoints for managing teachers and books.
5. Feel free to extend this project based on your specific requirements. The modular structure of Spring MVC allows easy customization and addition of features.

Feel free to extend and customize this system based on your specific requirements.

## Dependencies

This project is built using Maven and utilizes the following dependencies:

```xml
<dependencies>
    <!-- Spring Boot Actuator for production-ready features -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

    <!-- Spring Boot Starter for Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- Spring Boot Starter for Validation -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

    <!-- Spring Boot Starter for Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Boot DevTools for enhanced development experience -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>

    <!-- PostgreSQL JDBC Driver -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Lombok for reducing boilerplate code -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- Spring Boot Starter Test for testing support -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
