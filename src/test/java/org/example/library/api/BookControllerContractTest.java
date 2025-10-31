package org.example.library.api;

import com.atlassian.oai.validator.restassured.OpenApiValidationFilter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class BookControllerContractTest {

    @LocalServerPort
    private Integer port;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    private final OpenApiValidationFilter validationFilter = new OpenApiValidationFilter("src/main/resources/openapi/library-api.yaml");

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void createBook_shouldConformToOpenApiContract() {
        String requestBody = """
                {
                  "title": "Test Book",
                  "author": "Test Author",
                  "isbn": "978-3-16-148410-0",
                  "description": "Test Description",
                  "publicationYear": 2023,
                  "pageCount": 100
                }""";

        given()
                .filter(validationFilter)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/books")
                .then()
                .statusCode(201)
                .body("title", equalTo("Test Book"))
                .body("author", equalTo("Test Author"));
    }
}
