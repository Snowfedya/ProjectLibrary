# Library API Contract

This module contains the OpenAPI 3.0 specification for the Library Management System. It defines the API endpoints, data models, and security requirements for the entire application. The contract-first approach ensures that the API is well-documented and consistent across all services.

## Overview

The Library API provides a comprehensive set of endpoints for managing books, users, and borrowing records. It is designed to support a modern educational library with features for students, teachers, librarians, and administrators.

## User Roles and Permissions

The API defines four user roles with a clear hierarchy of permissions:

-   **MEMBER (Student)**: Can view books, borrow and return up to 5 books for 14 days, and view their own borrowing history.
-   **TEACHER**: Has all `MEMBER` permissions but with extended limits (10 books, 30 days). Can also create reading lists and supervise student groups.
-   **LIBRARIAN**: Has all `TEACHER` permissions and can manage the entire book catalog, handle all borrowing records, and manage fines.
-   **ADMIN**: Has all `LIBRARIAN` permissions plus the ability to manage user accounts and system settings.

## Getting Started

To generate the Java interfaces and DTOs from the OpenAPI specification, run the following Maven command in the root directory:

```bash
mvn clean install
```

The generated code will be available in the `target/generated-sources` directory of this module.

## API Usage Examples

### Authentication

**Login:**

```
POST /api/auth/login
Content-Type: application/json

{
  "username": "student_user",
  "password": "password123"
}
```

**Register as a MEMBER:**

```
POST /api/auth/register
Content-Type: application/json

{
  "username": "new_student",
  "email": "new.student@example.com",
  "password": "strongpassword",
  "fullName": "New Student"
}
```

### Searching for Books

**Search with filters:**

```
GET /api/books?q=Java&genre=Programming&availableOnly=true
```

### Borrowing a Book

```
POST /api/borrows
Authorization: Bearer <jwt_token>
Content-Type: application/json

{
  "bookId": 123
}
```
