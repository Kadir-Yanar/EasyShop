# EasyShop

EasyShop is a full-stack e-commerce application built as a capstone project. It features a Java Spring Boot REST API backend and a modern HTML/JavaScript frontend. The project supports user authentication, product browsing, shopping cart management, and more.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
  - [Backend Setup](#backend-setup)
  - [Frontend Setup](#frontend-setup)
- [API Overview](#api-overview)
- [Database Schema](#database-schema)
- [Authentication](#authentication)
- [Testing](#testing)
- [Troubleshooting](#troubleshooting)

---

## Features

- User registration and login (JWT-based authentication)
- Product catalog with categories and search/filtering
- Shopping cart (add, update, remove items)
- Profile management
- Order placement (optional/extension)
- Admin features (optional/extension)

## Tech Stack

- **Backend:** Java 17, Spring Boot 2.7, Spring Security, JDBC, MySQL or SQL Server
- **Frontend:** HTML, CSS (Bootstrap), JavaScript (Axios, Mustache.js, jQuery)
- **Build Tool:** Maven

## Project Structure

```
EasyShop/
  capstone-starter/                # Spring Boot backend
    src/main/java/org/yearup/      # Java source code
    src/main/resources/            # Application properties
    database/                      # SQL scripts
    pom.xml                        # Maven config
  capstone-client-web-application/ # Frontend (static site)
    index.html
    js/                            # JavaScript services and logic
    css/                           # Stylesheets
    images/                        # Product images
```

## Getting Started

### Backend Setup

1. **Install Java 17 and Maven**  
   Make sure you have Java 17 and Maven installed.

2. **Database Setup**  
   - Use MySQL or SQL Server.
   - Run the SQL script at `capstone-starter/database/create_database.sql` to create and populate the database.
   - Update `src/main/resources/application.properties` with your database connection details.

3. **Build and Run the Backend**
   ```bash
   cd capstone-starter
   mvn clean install
   mvn spring-boot:run
   ```
   The API will start on [http://localhost:8080](http://localhost:8080).

### Frontend Setup

1. **No build step required.**  
   The frontend is a static site.

2. **Serve the frontend**  
   - Open `capstone-client-web-application/index.html` directly in your browser, or
   - Use a simple HTTP server (e.g., VSCode Live Server, Python's `http.server`, or Node's `http-server`).

3. **Configuration**  
   The frontend expects the backend API at `http://localhost:8080`. You can change this in `js/config.js` if needed.

## API Overview

- `/products` - List and search products
- `/categories` - List categories
- `/shoppingcart` - Manage shopping cart (requires authentication)
- `/profile` - View and update user profile (requires authentication)
- `/auth/login` - User login (returns JWT)
- `/auth/register` - User registration

See the controllers in `capstone-starter/src/main/java/org/yearup/controllers/` for full details.

## Database Schema

Main tables:
- `users`, `profiles`
- `categories`, `products`
- `shopping_cart`
- `orders`, `order_line_items` (optional/extension)

See `capstone-starter/database/create_database.sql` for full schema and sample data.

## Authentication

- The API uses JWT for authentication.
- After logging in via `/auth/login`, include the JWT token in the `Authorization` header for protected endpoints:
  ```
  Authorization: Bearer <your_token>
  ```

## Testing

- Postman collections are provided in `capstone_postman_collections/` for API testing.
- Automated tests are in `capstone-starter/src/test/java/`.

## Troubleshooting

- **404 on root URL:** This is normal; use `/products`, `/categories`, etc.
- **401 Unauthorized:** Make sure to log in and include the JWT token in your requests.
- **Database errors:** Check your DB connection settings and ensure the schema is created.

---

**Enjoy building and exploring EasyShop!**

---

