#  Eventify - Event Management System

A RESTful API for managing events and participants built with **Spring Boot** and **Spring Data JPA**.


## Overview

**Eventify** is a simple yet powerful event management system designed for organizations to:
- Create and manage events (conferences, weddings, training sessions, etc.)
- Add participants via bulk file upload (CSV) and manually
- Search and filter events efficiently

## Features

### Event Management
- ✅ **Create Events** - Add new events with title, description, date, and location
- ✅ **Update Events** - Full update (PUT) or partial update (PATCH)
- ✅ **Delete Events** - Cascade delete removes all participants
- ✅ **List Events** - Retrieve all events or a single event by ID
- ✅ **Search Events** - Search by title, description, or location

### Participant Management
- ✅ **Bulk Upload** - Import participants via CSV or Excel files
- ✅ **List Participants** - Get all participants for a specific event

### API Features
- ✅ **RESTful Design** - Proper HTTP verbs and status codes
- ✅ **Swagger/OpenAPI** - Interactive API documentation
- ✅ **Error Handling** - Meaningful error messages
- ✅ **Data Validation** - Input validation with clear feedback

## 🛠 Tech Stack

| Technology | Purpose |
|------------|---------|
| **Spring Boot 3.2.0** | Application framework |
| **Spring Data JPA** | Data persistence layer |
| **H2 Database** | In-memory database (dev) |
| **Hibernate** | ORM implementation |
| **SpringDoc OpenAPI** | API documentation (Swagger) |
| **Apache Commons CSV** | CSV file processing |
| ** Validation** | Input validation

## 🚀 Getting Started

### Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- An IDE (IntelliJ IDEA, Eclipse, VS Code)

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/ace/eventify.git
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

## 📖 API Documentation

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
| POST | `/api/events/{eventId}/participants` | create a particpant | 201, 400, 404 |
| POST | `/api/events/{eventId}/participants/upload` | Upload participants file | 201, 400, 404 |

### Events Table

```sql
CREATE TABLE events (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    event_date TIMESTAMP NOT NULL,
    location VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
);
```

### Participants Table

```sql
CREATE TABLE participants (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(50),
    invitation_status VARCHAR(20) NOT NULL,
    event_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
    UNIQUE CONSTRAINT uk_event_email (event_id, email)
);
```

**Key Relationships:**
- One Event → Many Participants (One-to-Many)
- Cascade Delete: Deleting an event removes all its participants
- Unique Constraint: Email must be unique per event


## Error Handling

The API returns consistent error responses:

### 404 Not Found
```json
{
  "status": 404,
  "message": "Event not found with ID: 999",
  "timestamp": "2025-10-03T14:30:00",
  "path": "uri=/api/events/999"
}
```

### 400 Bad Request (Validation)
```json
{
  "status": 400,
  "message": "Validation failed",
  "timestamp": "2025-10-03T14:30:00",
  "path": "uri=/api/events",
  "errors": {
    "title": "Title is required",
    "eventDate": "Event date is required"
  }
}
```

### 400 Bad Request (Business Logic)
```json
{
  "status": 400,
  "message": "Participant with email john@example.com already exists for this event",
  "timestamp": "2025-10-03T14:30:00",
  "path": "uri=/api/events/1/participants/upload"
}
```

### 413 Payload Too Large
```json
{
  "status": 413,
  "message": "File size exceeds maximum allowed size",
  "timestamp": "2025-10-03T14:30:00",
  "path": "uri=/api/events/1/participants/upload"
}
```

### 500 Internal Server Error
```json
{
  "status": 500,
  "message": "An unexpected error occurred. Please try again later.",
  "timestamp": "2025-10-03T14:30:00",
  "path": "uri=/api/events"
}

## 👨‍💻 Author

**Anas Yakubu**
- GitHub: [@acekhalifa](https://github.com/acekhalifa)
- Email: anasyakubu687@gmail.com
