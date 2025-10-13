#  Eventify - Event Management System

A secured RESTful API for managing events and participants with JWT authentication and user ownership built with Spring Boot, Spring Data JPA and Spring Security with support(local) for HTTPS.


## Overview

**Eventify** is an event management system designed to:
- Create and manage events (conferences, weddings, training sessions, etc.)
- Add participants via bulk file upload or manually
- Search and filter events efficiently
- Register users/organizers who can perform the above operations
- Ensures creation and management of events and their associated participants can only be possible with logged in users
- Each logged in user only has access to create and manage their own events (scoped access).

## Features
### User Management
-  **Register Users** - Add new Users with name, email, and password
-  **Login Users** - allows created users to create and manage events by providing email and password

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
-  Invitation Status Tracking - PENDING, ACCEPTED, DECLINED

### API Features
- **RESTful Design** - Proper HTTP verbs and status codes
-  **Swagger/OpenAPI** - Interactive API documentation
- **Error Handling** - Meaningful error messages
-  **Data Validation** - Input validation with clear feedback


### API Security Features

- **Authentication required** for all endpoints except `/api/auth/**` 
- **User ownership enforcement** - Users can only access their own data  
- **Stateless sessions** - No server-side session storage  
- **Bearer token** authorization standard  
- **Input validation** on all request bodies
- **HTTPS enabled** all endpoints are accessed 


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

The application will start on `https://localhost:8443`

### Quick Test

Once running, access:
- **Swagger UI**: https://localhost:8443/swagger-ui.html
- **H2 Console**: https://localhost:8443/h2-console
  - JDBC URL: `jdbc:h2:mem:eventifydb`
  - Username: `sa`
  - Password: *(leave empty)*

## API Documentation

### Swagger UI

The API documentation is available at:

**https://localhost:8443/swagger-ui.html**

## API Endpoints

### Authentication (Public, No Authentication Required)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | User login |


### Events (Protected - Requires Login (JWT))

| Method | Endpoint | Description | Status Codes |
|--------|----------|-------------|--------------|
| POST | `/api/events` | Create new event | 201, 400, 401|
| GET | `/api/events` | Get all events | 200, 401, 401, 401 |
| GET | `/api/events/{id}` | Get event by ID | 200, 404, 401, |
| PUT | `/api/events/{id}` | Update event (full) | 200, 404, 400, 401 |
| PATCH | `/api/events/{id}` | Update event (partial) | 200, 404, 401 |
| DELETE | `/api/events/{id}` | Delete event | 204, 404, 401 |
| GET | `/api/events/search?keyword={keyword}` | Search events | 200, 401 |

### Participants (Protected - Requires Login (JWT))

| Method | Endpoint | Description | Status Codes |
|--------|----------|-------------|--------------|
| GET | `/api/events/{eventId}/participants` | Get all participants | 200, 404, 401 |
| POST | `/api/events/{eventId}/participants` | create a particpant | 201, 400, 401 |
| POST | `/api/events/{eventId}/participants/upload` | Upload participants file | 201, 400, 404, 401 |

## Pagination

All list endpoints support pagination with these query parameters:

| Parameter | Description |
|-----------|-------------|
| `page` | Page number (0-indexed) |
| `size` | Items per page | 
| `sort` | Sort field and direction 

## Authentication Flow

The Eventify authentication process uses a Stateless JWT (JSON Web Token) flow. Every request is verified using the token itself.

The 3-Step Process:
Step	Action	Endpoint & Headers	Description
1. Login/Register	The user sends credentials (email, password) to the public authentication endpoint.	POST /api/auth/login	The server verifies the credentials and, if valid, generates a signed JWT.
2. Receive Token	The user's client receives the JWT in the response body.	Response Body: { "token": "..." }, should in case a client is accessing the api, the client must securely store this token (e.g., an HTTP-only cookie).
3. Access Protected Data	The client sends the token with every request to a secured endpoint.	Request Header: Authorization: Bearer <JWT>	The JwtAuthenticationFilter intercepts the request, validates the token signature, extracts the user ID, and grants access to the requested resource.


## Local HTTPS Setup (Testing Security)


Since JWTs are sensitive, they should never be transmitted over unsecured HTTP. This project is configured to run on HTTPS locally for secure testing.

You must have a Java Keystore (.keystore) file containing your SSL certificate and private key.

### Configuration
A Keystore file containing the SSL certificate and private key required for https to work is configured in the  resources folder and the application.properties file

### Accessing the Application
After running mvn spring-boot:run, the application will be accessible via prefixing HTTPS to all endpoints e.g.:
Swagger UI: https://localhost:8443/swagger-ui.html

### Security Warning (Self-Signed Certificate)
Because the application is configured with a local, self-signed certificate, your browser (Chrome, Firefox, etc.) will display a security warning ("Your connection is not private") when you first access https://localhost:8443.

Action: You must click the "Advanced" or "Proceed/Accept the Risk" option to bypass the warning and continue to the application. This is expected behavior for local development certificates.


## Author

**Anas Yakubu**
- GitHub: [@acekhalifa](https://github.com/acekhalifa)
- Email: anasyakubu687@gmail.com
