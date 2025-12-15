# 📖 Recipes Book API

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![JWT](https://img.shields.io/badge/Security-JWT-red)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)

A backend REST API application for a personal recipes book. It allows users to manage their recipes, search through the collection, and maintain a "Favorites" list. The application is secured with JWT authentication.

## 🚀 Features

* **Authentication & Security:**
    * User Registration and Login (JWT Authentication).
    * Passwords are encrypted using BCrypt.
    * **Ownership Protection:** Users can only update or delete their own recipes.
* **Recipes Management (CRUD):**
    * Create, Read, Update, and Delete recipes.
    * **Advanced Search:** Filter recipes by title (case-insensitive, partial match).
    * **Pagination:** Efficiently load large lists of recipes using page and size parameters.
* **Favorites:**
    * Add/Remove recipes to/from the "Favorites" list.
    * View the current user's favorite recipes.
* **Documentation:**
    * Interactive API documentation via Swagger UI (OpenAPI).

## 🛠 Tech Stack

* **Core:** Java 21, Spring Boot 3
* **Database:** PostgreSQL, Spring Data JPA, Hibernate
* **Security:** Spring Security, JWT (jjwt library)
* **Utils:** Lombok, Maven, Docker
* **Docs:** SpringDoc OpenAPI (Swagger)

## 📖 API Documentation (Swagger)

Once the application is running, you can access the interactive API documentation here:
`http://localhost:8080/swagger-ui/index.html`

## ⚙️ Getting Started

### Prerequisites
* Java 21
* Maven
* PostgreSQL (or Docker)

### Option 1: Run with Docker (Recommended)
You don't need to install PostgreSQL manually. Just run:

```bash
# 1. Build the JAR file
mvn clean package -DskipTests

# 2. Start the containers
docker-compose up --build
```

### Option 2: Run Manually
1.  Ensure PostgreSQL is running and create a database named `recipes_book_db`.
2.  Configure your database credentials in `src/main/resources/application.properties`.
3.  Run the application:
    ```bash
    mvn spring-boot:run
    ```

## 🔌 API Usage Examples

**1. Register a new user:**
`POST /api/auth/registration`
```json
{
  "username": "chef_john",
  "email": "john@example.com",
  "login": "best_chef_in_the_world",
  "password": "strongPassword123"
}
```

**2. Login:**
`POST /api/auth/login`
*(Returns a JWT Token. Use this token in the "Authorization: Bearer <token>" header for subsequent requests)*

**3. Search Recipes (with Pagination):**
`GET /api/recipes/find?title=soup&page=0&size=5`

---
*Created by Illia Rymtsou*
