package org.example.library.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.example.library.domain.Book;
import org.example.library.dto.BookRequest;
import org.example.library.repositories.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        bookRepository.deleteAll();
        book = new Book();
        book.setTitle("Integration Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("1234567890123");
        book = bookRepository.save(book);
    }

    @AfterEach
    public void tearDown() {
        bookRepository.deleteAll();
    }

    @Test
    public void whenGetBookById_andBookExists_thenBookIsReturned() {
        given()
                .pathParam("id", book.getId())
                .when()
                .get("/api/v1/books/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(book.getId().toString()))
                .body("title", equalTo(book.getTitle()));
    }

    @Test
    public void whenGetBookById_andBookDoesNotExist_thenNotFoundIsReturned() {
        given()
                .pathParam("id", UUID.randomUUID())
                .when()
                .get("/api/v1/books/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void whenCreateBook_thenBookIsCreated() {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("New Book");
        bookRequest.setAuthor("New Author");
        bookRequest.setIsbn("9876543210987");

        given()
                .contentType(ContentType.JSON)
                .body(bookRequest)
                .when()
                .post("/api/v1/books")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("title", equalTo("New Book"));
    }
}
