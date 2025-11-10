# AGENTS.md - ProjectLibrary

## Project Overview
ProjectLibrary - это система управления библиотекой на Spring Boot 3.x с PostgreSQL, предоставляющая REST API для управления книгами, пользователями и займами.

**Repository:** https://github.com/Snowfedya/ProjectLibrary  
**Branch:** fedya  
**Tech Stack:** Spring Boot 3.x, Java 17, PostgreSQL, Spring Data JPA, Spring Security, JWT

## Current Enhancement Phase
**Status:** Implementing 7 major features (Week 1-4 roadmap)  
**Current Feature:** Feature 1 - Grafana Monitoring Dashboard  
**Current Step:** 1.2 - Creating custom metrics infrastructure

## Project Structure
```
ProjectLibrary/
├── src/main/java/com/library/
│   ├── config/          # Configuration classes
│   ├── controller/      # REST Controllers
│   ├── entity/          # JPA Entities
│   ├── repository/      # Spring Data Repositories
│   ├── service/         # Business Logic Services
│   ├── dto/             # Data Transfer Objects
│   └── security/        # Security configurations
├── src/main/resources/
│   ├── application.properties
│   └── templates/       # Thymeleaf email templates
└── pom.xml             # Maven dependencies
```

## Build & Run Commands
```
# Build
mvn clean package -DskipTests

# Run
mvn spring-boot:run

# Test
mvn test
mvn verify -P integration-tests

# Docker
docker-compose up -d
```

## Coding Conventions
- **Language:** Java 17+ with modern features
- **Framework:** Spring Boot 3.x best practices
- **Naming:** 
  - Classes: PascalCase (e.g., `MetricsService`)
  - Methods: camelCase (e.g., `recordBookBorrowed()`)
  - Constants: UPPER_SNAKE_CASE
- **Annotations:** Use Spring annotations (@Service, @RestController, @Autowired, @Transactional)
- **Error Handling:** Custom exceptions with @ControllerAdvice
- **Validation:** Use Bean Validation (@Valid, @NotNull, @Size)
- **Logging:** Use SLF4J with @Slf4j annotation

## Package Structure Guidelines
- `config/` - @Configuration classes, beans, metrics setup
- `controller/` - @RestController, REST endpoints
- `service/` - @Service, business logic, @Transactional
- `repository/` - Extends JpaRepository
- `entity/` - @Entity, JPA mappings
- `dto/` - Request/Response objects

## Dependencies Management
- Use Maven (pom.xml)
- Spring Boot Starter Parent: 3.x
- Always specify versions in `<properties>` section
- Group related dependencies together with comments

## Testing Requirements
- Unit tests: JUnit 5 + Mockito
- Integration tests: TestContainers for database
- Target coverage: 85%+
- Test class naming: `{ClassName}Test`
- Test method naming: `should{ExpectedBehavior}_when{Condition}`

## Current Enhancement Checklist

### Feature 1: Grafana Monitoring (IN PROGRESS)
- [x] Add Actuator dependencies
- [x] Configure Prometheus endpoint
- [ ] **CURRENT:** Create MetricsConfig.java
- [ ] **CURRENT:** Create MetricsService.java
- [ ] Integrate metrics into services
- [ ] Create docker-compose for Prometheus/Grafana
- [ ] Create Grafana dashboard JSON

### Feature 2: Load Testing (PLANNED)
### Feature 3: Password Recovery (PLANNED)
### Feature 4: Book Collections (PLANNED)
### Feature 5: Overdue Notifications (PLANNED)
### Feature 6: Admin Management (PLANNED)
### Feature 7: ELK Logging (PLANNED)

## Agent-Specific Instructions

### When Creating New Classes:
1. Always add package declaration
2. Add necessary imports
3. Use appropriate Spring annotations
4. Add Lombok annotations (@Slf4j, @RequiredArgsConstructor) when applicable
5. Include JavaDoc for public methods
6. Follow existing project patterns

### When Modifying Existing Code:
1. Check existing patterns in similar classes
2. Maintain consistent code style
3. Update related tests
4. Don't break existing functionality

### For Metrics Implementation:
- Use Micrometer API (io.micrometer.core.instrument)
- Counter for incrementing operations
- Gauge for current state values
- Timer for duration measurements
- Tag metrics appropriately (endpoint, method, status)

## Key Files to Reference
- `application.properties` - Configuration
- `pom.xml` - Dependencies
- `SecurityConfig.java` - Security setup (if exists)
- Existing Service classes - for patterns

## Environment Variables
```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/library_db
SPRING_DATASOURCE_USERNAME=library_user
SPRING_DATASOURCE_PASSWORD=library_pass
```

## Important Notes
- Always use `@Transactional` for database operations in services
- Use `Optional<>` for repository methods that might return null
- Prefer constructor injection over field injection
- Use DTOs for API requests/responses, not entities directly
- Implement proper error handling with custom exceptions

---
**Last Updated:** 2025-11-10  
**Current Agent:** Jules.ai  
**Current Task:** Feature 1.2 - Custom Metrics Infrastructure
```
