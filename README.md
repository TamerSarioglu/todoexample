# Todo Example - Spring Boot Kotlin REST API

A simple yet complete RESTful API built with **Spring Boot** and **Kotlin** for managing todos with JWT authentication. This is a great starter project to learn Spring Boot with Kotlin!

## 🚀 Features

- **User Authentication** with JWT (JSON Web Tokens)
- **User Registration** and Login
- **CRUD Operations** for todos
- **Spring Security** for authentication and authorization
- **H2 In-Memory Database** for easy development
- **Spring Data JPA** for database operations
- RESTful API design

## 📋 Prerequisites

Before running this project, make sure you have:

- **Java 21** or higher
- **Gradle** (or use the included Gradle wrapper)

## 🛠️ Technologies Used

- **Kotlin 1.9.25** - Modern JVM language
- **Spring Boot 3.5.6** - Application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database operations
- **H2 Database** - In-memory database
- **JWT (jjwt 0.12.6)** - Token-based authentication
- **Gradle** - Build tool

## 📦 Project Structure

```
src/main/kotlin/com/tamersarioglu/todoexample/
├── config/
│   ├── JwtAuthFilter.kt        # JWT authentication filter
│   ├── JwtUtil.kt              # JWT utility for token generation/validation
│   └── SecurityConfig.kt       # Spring Security configuration
├── controller/
│   ├── AuthController.kt       # Authentication endpoints
│   └── TodoController.kt       # Todo CRUD endpoints
├── model/
│   ├── Todo.kt                 # Todo entity
│   └── User.kt                 # User entity
├── repository/
│   ├── TodoRepository.kt       # Todo data access
│   └── UserRepository.kt       # User data access
├── service/
│   └── UserDetailsServiceImpl.kt # User details service for Spring Security
└── TodoexampleApplication.kt   # Main application entry point
```

## 🚦 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/TamerSarioglu/todoexample.git
cd todoexample
```

### 2. Run the application

**Using Gradle Wrapper (Recommended):**

On Windows:
```bash
.\gradlew.bat bootRun
```

On Linux/Mac:
```bash
./gradlew bootRun
```

**Or build and run:**
```bash
.\gradlew.bat build
java -jar build/libs/todoexample-0.0.1-SNAPSHOT.jar
```

### 3. Access the application

The application will start on `http://localhost:8080`

## 📚 API Endpoints

### Authentication Endpoints

#### Register a new user
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "john",
  "password": "password123"
}
```

**Response:**
```
User registered successfully
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "john",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Todo Endpoints (Requires Authentication)

For all todo endpoints, include the JWT token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

#### Create a new todo
```http
POST /api/todos
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "title": "Learn Spring Boot",
  "description": "Complete the tutorial and build a REST API"
}
```

**Response:**
```json
{
  "id": 1,
  "title": "Learn Spring Boot",
  "description": "Complete the tutorial and build a REST API",
  "userName": "john",
  "completed": false
}
```

#### Get all todos for the authenticated user
```http
GET /api/todos
Authorization: Bearer <your-jwt-token>
```

**Response:**
```json
[
  {
    "id": 1,
    "title": "Learn Spring Boot",
    "description": "Complete the tutorial and build a REST API",
    "userName": "john",
    "completed": false
  }
]
```

## 🧪 Testing with cURL

Here's a complete workflow example:

```bash
# 1. Register a new user
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"john\",\"password\":\"password123\"}"

# 2. Login to get JWT token
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"john\",\"password\":\"password123\"}"

# 3. Create a todo (replace <TOKEN> with the token from login)
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN>" \
  -d "{\"title\":\"My First Todo\",\"description\":\"This is a test todo\"}"

# 4. Get all todos
curl -X GET http://localhost:8080/api/todos \
  -H "Authorization: Bearer <TOKEN>"
```

## 🧪 Testing with Postman

1. **Register**: POST to `http://localhost:8080/api/auth/register` with JSON body
2. **Login**: POST to `http://localhost:8080/api/auth/login` and copy the token from response
3. **Create Todo**: POST to `http://localhost:8080/api/todos` with Authorization header `Bearer <token>`
4. **Get Todos**: GET to `http://localhost:8080/api/todos` with Authorization header

## ⚙️ Configuration

The application is configured via `src/main/resources/application.properties`:

```properties
# Application name
spring.application.name=todoexample

# H2 Database (in-memory)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# JWT Configuration
jwt.secret=<your-secret-key>
jwt.expiration=86400000  # 24 hours in milliseconds
```

**Note:** The database is in-memory, so all data will be lost when you stop the application.

## 🛠️ Troubleshooting

### Port 8080 is already in use

If you see this error:
```
Web server failed to start. Port 8080 was already in use.
```

**Solution 1: Stop the process using port 8080**

On Windows:
```bash
# Find which process is using port 8080
netstat -ano | findstr :8080

# Kill the process (replace PID with the actual process ID)
taskkill /F /PID <PID>
```

On Linux/Mac:
```bash
# Find which process is using port 8080
lsof -i :8080

# Kill the process (replace PID with the actual process ID)
kill -9 <PID>
```

**Solution 2: Change the port**

Add this line to `src/main/resources/application.properties`:
```properties
server.port=8081
```

Then access the application at `http://localhost:8081`

### Database connection issues

If you're having database issues, make sure the H2 configuration in `application.properties` is correct. The application uses an in-memory database that's created automatically on startup.

## 🔐 Security

- Passwords are encrypted using BCrypt before storing in the database
- JWT tokens expire after 24 hours (configurable)
- Authentication is required for all todo endpoints
- Each user can only access their own todos

## 📖 Learning Resources

As this is your first Spring Boot Kotlin project, here are some helpful resources:

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Spring Security with JWT](https://spring.io/guides/gs/securing-web/)
- [Spring Data JPA](https://spring.io/guides/gs/accessing-data-jpa/)

## 🤝 Contributing

Feel free to fork this project and make your own modifications!

## 📝 License

This is a learning project - feel free to use it however you'd like!

## 🎯 Future Enhancements

Ideas for expanding this project:
- Add update and delete operations for todos
- Add todo completion toggle endpoint
- Implement todo categories or tags
- Add pagination for todo lists
- Switch from H2 to PostgreSQL or MySQL
- Add unit and integration tests
- Add API documentation with Swagger/OpenAPI
- Implement refresh tokens
- Add email verification for registration

---

**Happy Coding!** 🎉 This is just the beginning of your Spring Boot + Kotlin journey!