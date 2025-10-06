#  Eventify - Event Management System

A RESTful API for managing events and participants built with **Spring Boot** and **Spring Data JPA**.


## Overview

**Eventify** is an event management system designed to:
- Create and manage events (conferences, weddings, training sessions, etc.)
- Add participants via bulk file upload or manually
- Search and filter events efficiently

## Features

### Event Management
-  **Create Events** - Add new events with title, description, date, and location
-  **Update Events** - Full update (PUT) or partial update (PATCH)
-  **Delete Events** - Cascade delete removes all participants
-  **List Events** - Retrieve all events or a single event by ID
-  **Search Events** - Search by title, description, or location

### Participant Management
-  **Bulk Upload** - Import participants via CSV file
   **Manual Upload** - Create a participant for an event
-  **List Participants** - Get all participants for a specific event

### API Features
- **RESTful Design** - Proper HTTP verbs and status codes
-  **Swagger/OpenAPI** - Interactive API documentation
- **Error Handling** - Meaningful error messages
-  **Data Validation** - Input validation with clear feedback

## Technologies used

| Technology | Purpose |
|------------|---------|
| **Spring Boot** | Application framework |
| **Spring Data JPA** | Data persistence layer |
| **H2 Database** | In-memory database |
| **Hibernate** | ORM implementation |
| **SpringDoc OpenAPI** | API documentation (Swagger) |
| **Apache Commons CSV** | CSV file processing |
| **Java JDK 25** | Primary Language |
| **Maven 3.9.11** | Build tool |
| **Jakarta Validation** | Input validation |


### Installation

1. **Clone the repository**
```bash
git clone https://github.com/acekhalifa/eventify.git
cd eventify
```

2. **Build the project**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Quick Test

Once running, access:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:eventifydb`
  - Username: `sa`
  - Password: *(leave empty)*

## API Documentation

### Swagger UI

The complete, interactive API documentation is available at:

**http://localhost:8080/swagger-ui.html**

You can:
- View all endpoints with request/response schemas
- Test endpoints directly from the browser
- See example requests and responses

## API Endpoints

### Events

| Method | Endpoint | Description | Status Codes |
|--------|----------|-------------|--------------|
| POST | `/api/events` | Create new event | 201, 400 |
| GET | `/api/events` | Get all events | 200 |
| GET | `/api/events/{id}` | Get event by ID | 200, 404 |
| PUT | `/api/events/{id}` | Update event (full) | 200, 404, 400 |
| PATCH | `/api/events/{id}` | Update event (partial) | 200, 404 |
| DELETE | `/api/events/{id}` | Delete event | 204, 404 |
| GET | `/api/events/search?keyword={keyword}` | Search events | 200 |

### Participants

| Method | Endpoint | Description | Status Codes |
|--------|----------|-------------|--------------|
| GET | `/api/events/{eventId}/participants` | Get all participants | 200, 404 |
| POST | `/api/events/{eventId}/participants` | create a particpant | 201, 400 |
| POST | `/api/events/{eventId}/participants/upload` | Upload participants file | 201, 400, 404 |



## 👨‍💻 Author

**Anas Yakubu**
- GitHub: [@acekhalifa](https://github.com/acekhalifa)
- Email: anasyakubu687@gmail.com
