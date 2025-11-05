# AGENTS GUIDE - ProjectLibrary Contract-First Implementation

## Project Context
This is a library management system being refactored to use Contract-First REST API approach. The project is split into two parts:
1. `library-api-contract` - OpenAPI specification and generated interfaces
2. `library-service` - main application implementing the contract

## Key Requirements
- Java 23, Spring Boot 3.3.5
- PostgreSQL database
- Contract-first approach with OpenAPI 3.0
- Docker containerization
- 85%+ test coverage mandatory
- Security considerations in all implementations

## Current Tech Stack
- Spring Boot 3.3.5
- Spring Data JPA
- Spring Security
- PostgreSQL
- Thymeleaf (will be replaced with REST API)
- Maven

## Architecture Decisions Made
- Using OpenAPI Generator Maven Plugin for code generation
- Multi-module Maven project structure
- Docker multi-stage builds
- Separate API contract repository pattern
