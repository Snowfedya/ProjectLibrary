package org.example.library.controllers.assemblers;

import org.example.library.models.Author;
import org.example.library.models.Book;
import org.example.library.models.BookAuthor;
import org.example.library.models.DTO.BookDTO;
import org.example.library.models.DTO.BookResponse;
import org.example.library.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookResponseAssemblerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookResponseAssembler bookResponseAssembler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private void mockSecurityContext(String role) {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        when(authentication.getAuthorities()).thenReturn((Collection) Collections.singletonList(authority));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void givenAdminRole_whenToModel_thenAllLinksPresent() {
        mockSecurityContext("ROLE_ADMIN");
        Book book = new Book();
        book.setBookId(1L);
        Author author = new Author();
        author.setAuthorId(1L);
        BookAuthor bookAuthor = new BookAuthor();
        bookAuthor.setAuthor(author);
        book.setBookAuthors(List.of(bookAuthor));

        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(1L);

        when(bookService.convertToDTO(book, null)).thenReturn(bookDTO);

        BookResponse response = bookResponseAssembler.toModel(book);

        assertNotNull(response);
        assertTrue(response.getLink("self").isPresent());
        assertTrue(response.getLink("update").isPresent());
        assertTrue(response.getLink("delete").isPresent());
        assertTrue(response.getLink("self").isPresent());
    }

    @Test
    public void givenTeacherRole_whenToModel_thenUpdateLinkPresent() {
        mockSecurityContext("ROLE_TEACHER");
        Book book = new Book();
        book.setBookId(1L);
        Author author = new Author();
        author.setAuthorId(1L);
        BookAuthor bookAuthor = new BookAuthor();
        bookAuthor.setAuthor(author);
        book.setBookAuthors(List.of(bookAuthor));

        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(1L);

        when(bookService.convertToDTO(book, null)).thenReturn(bookDTO);

        BookResponse response = bookResponseAssembler.toModel(book);

        assertNotNull(response);
        assertTrue(response.getLink("self").isPresent());
        assertTrue(response.getLink("update").isPresent());
        assertFalse(response.getLink("delete").isPresent());
        assertTrue(response.getLink("self").isPresent());
    }

    @Test
    public void givenUserRole_whenToModel_thenConditionalLinksNotPresent() {
        mockSecurityContext("ROLE_USER");
        Book book = new Book();
        book.setBookId(1L);
        Author author = new Author();
        author.setAuthorId(1L);
        BookAuthor bookAuthor = new BookAuthor();
        bookAuthor.setAuthor(author);
        book.setBookAuthors(List.of(bookAuthor));
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(1L);

        when(bookService.convertToDTO(book, null)).thenReturn(bookDTO);
        BookResponse response = bookResponseAssembler.toModel(book);

        assertNotNull(response);
        assertTrue(response.getLink("self").isPresent());
        assertFalse(response.getLink("update").isPresent());
        assertFalse(response.getLink("delete").isPresent());
        assertTrue(response.getLink("self").isPresent());
    }
}
