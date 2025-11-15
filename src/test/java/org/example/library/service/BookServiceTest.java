package org.example.library.service;

import org.example.library.domain.Book;
import org.example.library.dto.BookRequest;
import org.example.library.dto.BookResponse;
import org.example.library.exception.ResourceNotFoundException;
import org.example.library.repositories.BookRepository;
import org.example.library.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void whenGetBookById_andBookExists_thenBookShouldBeReturned() {
        UUID bookId = UUID.randomUUID();
        Book book = new Book();
        book.setId(bookId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        BookResponse bookResponse = bookService.getBookById(bookId);

        assertNotNull(bookResponse);
        assertEquals(bookId, bookResponse.getId());
    }

    @Test
    void whenGetBookById_andBookDoesNotExist_thenResourceNotFoundExceptionShouldBeThrown() {
        UUID bookId = UUID.randomUUID();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(bookId));
    }

    @Test
    void whenCreateBook_thenBookShouldBeCreated() {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("Test Title");
        bookRequest.setAuthor("Test Author");

        Book book = new Book();
        book.setId(UUID.randomUUID());
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookResponse bookResponse = bookService.createBook(bookRequest);

        assertNotNull(bookResponse);
        assertEquals(book.getId(), bookResponse.getId());
        assertEquals(book.getTitle(), bookResponse.getTitle());
    }

    @Test
    void whenUpdateBook_andBookExists_thenBookShouldBeUpdated() {
        UUID bookId = UUID.randomUUID();
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("Updated Title");

        Book existingBook = new Book();
        existingBook.setId(bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookResponse bookResponse = bookService.updateBook(bookId, bookRequest);

        assertNotNull(bookResponse);
        assertEquals(bookId, bookResponse.getId());
        assertEquals(bookRequest.getTitle(), bookResponse.getTitle());
    }

    @Test
    void whenDeleteBook_andBookExists_thenBookShouldBeDeleted() {
        UUID bookId = UUID.randomUUID();
        when(bookRepository.existsById(bookId)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(bookId);

        bookService.deleteBook(bookId);

        verify(bookRepository, times(1)).deleteById(bookId);
    }
}
