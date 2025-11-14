package org.example.library.services;

import org.example.library.domain.Book;
import org.example.library.dto.BookRequest;
import org.example.library.dto.BookResponse;
import org.example.library.dto.PageBookResponse;
import org.example.library.exception.ResourceNotFoundException;
import org.example.library.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public PageBookResponse getBooks(int page, int size, String search) {
        logger.info("Fetching books with page: {}, size: {}, search: '{}'", page, size, search);
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage;
        if (search != null && !search.isEmpty()) {
            bookPage = bookRepository.findByTitleContainingIgnoreCase(search, pageable);
        } else {
            bookPage = bookRepository.findAll(pageable);
        }

        List<BookResponse> bookResponses = bookPage.getContent().stream()
                .map(this::convertToBookResponse)
                .collect(Collectors.toList());

        return PageBookResponse.builder()
                .content(bookResponses)
                .totalElements(bookPage.getTotalElements())
                .totalPages(bookPage.getTotalPages())
                .currentPage(bookPage.getNumber())
                .pageSize(bookPage.getSize())
                .build();
    }

    public BookResponse getBookById(UUID id) {
        logger.info("Fetching book with id: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        logger.info("Found book: {}", book);
        return convertToBookResponse(book);
    }

    public BookResponse createBook(BookRequest bookRequest) {
        logger.info("Creating a new book: {}", bookRequest);
        Book book = new Book();
        BeanUtils.copyProperties(bookRequest, book);
        book = bookRepository.save(book);
        logger.info("Created book: {}", book);
        return convertToBookResponse(book);
    }

    public BookResponse updateBook(UUID id, BookRequest bookRequest) {
        logger.info("Updating book with id: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        BeanUtils.copyProperties(bookRequest, book);
        book = bookRepository.save(book);
        logger.info("Updated book: {}", book);
        return convertToBookResponse(book);
    }

    public void deleteBook(UUID id) {
        logger.info("Deleting book with id: {}", id);
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
        logger.info("Deleted book with id: {}", id);
    }

    private BookResponse convertToBookResponse(Book book) {
        BookResponse bookResponse = new BookResponse();
        BeanUtils.copyProperties(book, bookResponse);
        return bookResponse;
    }
}
