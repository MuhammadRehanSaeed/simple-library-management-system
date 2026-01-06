# Library Management System (library_ms)

A Spring Boot backend project for managing a library system with books, users, and borrow records. The system uses **Spring Data JPA** for database interactions with **MySQL** and provides REST APIs for CRUD operations. It also automatically updates the status of borrowed books based on due dates and return dates.

---

## Features

- **User Management**
  - Add, update, delete, and fetch users
- **Book Management**
  - Add, update, delete, and fetch books
  - Supports pagination
- **Borrow Records**
  - Create borrow records linking books and users
  - Automatic status update:
    - **BORROWED** – currently borrowed
    - **OVERDUE** – due date passed and not returned
    - **RETURNED** – book has been returned
- **DTOs and API Responses**
  - Clean separation between entities and API responses
  - Consistent `ApiResponse` wrapper for all endpoints
- **Exception Handling**
  - Custom exceptions for not found users/books/borrow records

---

## Technologies

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Hibernate ORM
- MySQL
- Maven
- Lombok
- Jakarta Persistence API
- Spring Web (REST)

---

## API Endpoints

### Users
- `POST /api/users` – create user
- `GET /api/users/{id}` – get user by ID
- `PUT /api/users/{id}` – update user
- `DELETE /api/users/{id}` – delete user

### Books
- `POST /books` – create book
- `GET /books/{id}` – get book by ID
- `GET /books` – list all books (supports pagination with `?page=` and `?size=`)
- `PUT /books/{id}` – update book
- `DELETE /books/{id}` – delete book

### Borrow Records
- `POST /api/borrows` – create borrow record
- `GET /api/borrows/{id}` – fetch borrow record by ID (status updates automatically based on dates)

---

## Example Borrow Record JSON (POST)

```json
{
  "userId": 1,
  "bookId": 1,
  "borrowDate": "2026-01-06",
  "dueDate": "2026-01-13",
  "returnDate": "2026-01-12",
  "status": "BORROWED"
}
