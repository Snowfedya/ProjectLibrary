package org.example.library.controllers.assemblers;

import org.example.library.controllers.AuthorController;
import org.example.library.controllers.BookController;
import org.example.library.controllers.RatingController;
import org.example.library.models.Book;
import org.example.library.models.DTO.BookDTO;
import org.example.library.models.DTO.BookResponse;
import org.example.library.services.BookService;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookResponseAssembler extends RepresentationModelAssemblerSupport<Book, BookResponse> {

    private final BookService bookService;

    public BookResponseAssembler(BookService bookService) {
        super(BookController.class, BookResponse.class);
        this.bookService = bookService;
    }

    public BookResponse toModel(Book book, Long userId) {
        BookDTO dto = bookService.convertToDTO(book, userId);
        BookResponse response = toModel(dto);
        response.add(linkTo(methodOn(BookController.class).getBookById(book.getBookId())).withSelfRel());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            boolean isAdmin = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            boolean isTeacher = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"));

            if (isTeacher || isAdmin) {
                response.add(linkTo(methodOn(BookController.class).updateBook(book.getBookId(), null, null, 0, null, null, null, null, null, null, null)).withRel("update"));
            }

            if (isAdmin) {
                response.add(linkTo(methodOn(BookController.class).deleteBook(book.getBookId(), null)).withRel("delete"));
            }
        }
        if (!book.getBookAuthors().isEmpty()) {
            response.add(linkTo(methodOn(AuthorController.class).getAuthorById(book.getBookAuthors().get(0).getAuthor().getAuthorId())).withRel("authors"));
        }

        return response;
    }

    @Override
    public BookResponse toModel(Book book) {
        return toModel(book, null);
    }

    private BookResponse toModel(BookDTO dto) {
        BookResponse response = new BookResponse();
        response.setBookId(dto.getBookId());
        response.setTitle(dto.getTitle());
        response.setIsbn(dto.getIsbn());
        response.setPublicationYear(dto.getPublicationYear());
        response.setDescription(dto.getDescription());
        response.setPublisher(dto.getPublisher());
        response.setStatus(dto.getStatus());
        response.setAuthorNames(dto.getAuthorNames());
        response.setAverageRating(dto.getAverageRating());
        response.setUserRating(dto.getUserRating());
        response.setAddedById(dto.getAddedById());
        return response;
    }
}
