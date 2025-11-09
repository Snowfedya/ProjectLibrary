package org.example.library.controllers;

import org.example.library.controllers.assemblers.BookResponseAssembler;
import org.example.library.models.Book;
import org.example.library.models.DTO.BookDTO;
import org.example.library.services.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import(BookResponseAssembler.class)
public class BookControllerHateoasTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private UserService userService;

    @MockBean
    private FacultyService facultyService;

    @Test
    @WithMockUser(roles = "USER")
    public void givenUserRole_whenGetBookById_thenReturnsBookWithUserLinks() throws Exception {
        Book book = new Book();
        book.setBookId(1L);
        book.setBookAuthors(Collections.emptyList());
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(1L);

        when(bookService.getBookById(anyLong())).thenReturn(book);
        when(bookService.convertToDTO(any(Book.class), any())).thenReturn(bookDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.update").doesNotExist())
                .andExpect(jsonPath("$._links.delete").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenAdminRole_whenGetBookById_thenReturnsBookWithAdminLinks() throws Exception {
        Book book = new Book();
        book.setBookId(1L);
        book.setBookAuthors(Collections.emptyList());
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(1L);


        when(bookService.getBookById(anyLong())).thenReturn(book);
        when(bookService.convertToDTO(any(Book.class), any())).thenReturn(bookDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.update").exists())
                .andExpect(jsonPath("$._links.delete").exists());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenUserRole_whenGetAllBooks_thenReturnsPagedBooksWithUserLinks() throws Exception {
        Book book = new Book();
        book.setBookId(1L);
        book.setBookAuthors(Collections.emptyList());
        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(book), PageRequest.of(0, 1), 1);
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(1L);

        when(bookService.getAllBooks(0, 1)).thenReturn(bookPage);
        when(bookService.convertToDTO(any(Book.class), any())).thenReturn(bookDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/all?page=0&size=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.bookResponseList[0]._links.self").exists())
                .andExpect(jsonPath("$._embedded.bookResponseList[0]._links.update").doesNotExist())
                .andExpect(jsonPath("$._embedded.bookResponseList[0]._links.delete").doesNotExist());
    }
}
