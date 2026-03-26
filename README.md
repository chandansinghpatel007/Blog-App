# Blog Application (Backend)

A full-featured RESTful API for a Blogging platform built using **Spring Boot**. This application allows users to create accounts, write posts, categorize them, and leave comments.

## 🚀 Features
* **User Management**: Registration and login with role-based access control (ADMIN/NORMAL USER).
* **Post Management**: Full CRUD operations for blog posts (Create, Read, Update, Delete).
* **Category Management**: Organize posts into different categories.
* **Comment System**: Users can comment on various posts.
* **Security**: Integrated with **Spring Security** and **JWT (JSON Web Tokens)** for stateless authentication.
* **Search & Pagination**: Search posts by title/content and efficient data retrieval using Spring Data JPA Pagination.
* **File Handling**: Support for uploading and serving post images.

## 🛠️ Tech Stack
* **Java 17+**
* **Spring Boot 3.x**
* **Spring Security** (JWT)
* **Spring Data JPA**
* **MySQL / PostgreSQL**
* **Hibernate Validator** (for DTO validation)
* **ModelMapper** (for Entity-DTO mapping)

## 📋 Prerequisites
* JDK 17 or higher
* Maven 3.6+
* MySQL Server

## ⚙️ Configuration
Update the `src/main/resources/application.properties` file with your database details:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/blog_app_db
spring.datasource.username=your_username
spring.datasource.password=your_password

# JWT Secret
jwt.secret=your_secure_random_secret_key_here
jwt.expiration=604800000 

# File Upload Path
project.image=images/
```

## 🚀 Getting Started
1. **Clone the repository:**
   ```bash
   git clone https://github.com/chandansinghpatel007/Blog-App.git
   cd Blog-App
   ```

2. **Build the application:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

## 🔐 API Endpoints (Brief)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| POST | `/api/v1/auth/login` | Login and get JWT Token |
| POST | `/api/v1/users/` | Register a new user |
| GET | `/api/v1/posts/` | Get all posts (Paginated) |
| POST | `/api/v1/user/{userId}/category/{catId}/posts` | Create a new post |
| POST | `/api/v1/post/{postId}/comments` | Add a comment to a post |

## 🧪 Testing
Use the provided **Swagger UI** (if enabled) at:
`http://localhost:8080/swagger-ui/index.html`

Alternatively, import the Postman collection to test the secured endpoints by adding the Bearer Token in the Authorization header.

## 🤝 Contributing
Feel free to fork this project and submit pull requests for any features or improvements.
