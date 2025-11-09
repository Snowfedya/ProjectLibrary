package org.example.library.controllers.assemblers;

import org.example.library.controllers.AdminController;
import org.example.library.controllers.BookController;
import org.example.library.controllers.CommentController;
import org.example.library.controllers.RatingController;
import org.example.library.models.Book;
import org.example.library.models.DTO.BookDTO;
import org.example.library.models.LibraryUser;
import org.example.library.services.BookService;
import org.example.library.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookDtoAssembler extends RepresentationModelAssemblerSupport<Book, BookDTO> {

    private final CustomUserDetailsService customUserDetailsService;
    private final BookService bookService;

    @Autowired
    public BookDtoAssembler(CustomUserDetailsService customUserDetailsService, BookService bookService) {
        super(BookController.class, BookDTO.class);
        this.customUserDetailsService = customUserDetailsService;
        this.bookService = bookService;
    }

    @Override
    public BookDTO toModel(Book book) {
        Long currentUserId = customUserDetailsService.getCurrentUser() != null
                ? customUserDetailsService.getCurrentUser().getId()
                : null;
        BookDTO bookDTO = bookService.convertToDTO(book, currentUserId);

        // Self link
        bookDTO.add(linkTo(methodOn(BookController.class).getBookById(book.getBookId())).withSelfRel());

        // Comments link
        bookDTO.add(linkTo(methodOn(CommentController.class).getCommentsByBookId(book.getBookId())).withRel("comments"));

        // Ratings link
        bookDTO.add(linkTo(methodOn(RatingController.class).getRatingsForBook(book.getBookId())).withRel("ratings"));
        bookDTO.add(linkTo(methodOn(RatingController.class).rateBook(book.getBookId(), 0, null)).withRel("add-rating"));


        // Authors link
        bookDTO.add(linkTo(methodOn(AdminController.class).getAuthorsByBookId(book.getBookId())).withRel("authors"));


        // Conditional links based on user role
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            LibraryUser currentUser = customUserDetailsService.getCurrentUser().getLibraryUser();
            if (currentUser != null) {
                boolean isAdmin = currentUser.getRoles().stream()
                        .anyMatch(role -> role.getRoleName().equals("ADMIN"));
                boolean isTeacher = currentUser.getRoles().stream()
                        .anyMatch(role -> role.getRoleName().equals("TEACHER"));

                if (isAdmin) {
                    // Admin can update and delete any book
                    bookDTO.add(linkTo(methodOn(BookController.class).updateBook(book.getBookId(), null, null, 0, null, null, null, null, null, null, null)).withRel("update"));
                    bookDTO.add(linkTo(methodOn(BookController.class).deleteBook(book.getBookId(), null)).withRel("delete"));
                } else if (isTeacher) {
                    // Teacher can update books
                    bookDTO.add(linkTo(methodOn(BookController.class).updateBook(book.getBookId(), null, null, 0, null, null, null, null, null, null, null)).withRel("update"));
                }
            }
        }

        return bookDTO;
    }
}
