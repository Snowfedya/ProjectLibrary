package org.example.library.testing;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.library.models.*;
import org.example.library.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(2) // After default admin creation
public class SimpleTestDataGenerator implements CommandLineRunner {

    private final LibraryUserRepository userRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    
    private final Faker faker = new Faker(new Locale("ru"));

    @Override
    @Transactional
    public void run(String... args) {
        if (shouldGenerateTestData()) {
            log.info("Generating basic test data...");
            generateBasicTestData();
            log.info("Test data generation completed");
        }
    }

    private boolean shouldGenerateTestData() {
        // Generate test data only if database is nearly empty
        return userRepository.count() <= 1 && bookRepository.count() < 3;
    }

    @Transactional
    public void generateBasicTestData() {
        try {
            // Create roles first
            createRoles();
            
            // Create test users
            createTestUsers();
            
            // Create test books
            createTestBooks();
            
        } catch (Exception e) {
            log.error("Error generating test data", e);
        }
    }

    private void createRoles() {
        String[] roleNames = {"ADMIN", "LIBRARIAN", "TEACHER", "STUDENT"};
        
        for (String roleName : roleNames) {
            Role existingRole = roleRepository.findByRoleName(roleName);
            if (existingRole == null) {
                Role role = new Role(roleName);
                roleRepository.save(role);
                log.debug("Created role: {}", roleName);
            }
        }
    }

    private void createTestUsers() {
        // Create librarian
        if (userRepository.findByEmail("librarian@library.com") == null) {
            LibraryUser librarian = new LibraryUser();
            librarian.setUsername("librarian");
            librarian.setEmail("librarian@library.com");
            librarian.setPasswordHash(passwordEncoder.encode("librarian123"));
            
            Set<Role> librarianRoles = new HashSet<>();
            Role librarianRole = roleRepository.findByRoleName("LIBRARIAN");
            if (librarianRole != null) {
                librarianRoles.add(librarianRole);
            }
            librarian.setRoles(librarianRoles);
            
            userRepository.save(librarian);
            log.info("Created librarian user: librarian@library.com");
        }
        
        // Create test student
        if (userRepository.findByEmail("student@university.edu") == null) {
            LibraryUser student = new LibraryUser();
            student.setUsername("student");
            student.setEmail("student@university.edu");
            student.setPasswordHash(passwordEncoder.encode("student123"));
            
            Set<Role> studentRoles = new HashSet<>();
            Role studentRole = roleRepository.findByRoleName("STUDENT");
            if (studentRole != null) {
                studentRoles.add(studentRole);
            }
            student.setRoles(studentRoles);
            
            userRepository.save(student);
            log.info("Created student user: student@university.edu");
        }
    }

    private void createTestBooks() {
        // Create test authors if they don't exist
        Author tolstoy = findOrCreateAuthor("Лев", "Толстой");
        Author dostoevsky = findOrCreateAuthor("Федор", "Достоевский");

        // Create test books
        createBookIfNotExists("Война и мир", "978-5-17-123456-1", 1869, 
            "Великий роман Л.Н. Толстого", "Эксмо", tolstoy);

        createBookIfNotExists("Преступление и наказание", "978-5-17-123456-2", 1866, 
            "Психологический роман Ф.М. Достоевского", "АСТ", dostoevsky);

        log.info("Test books created successfully");
    }

    private Author findOrCreateAuthor(String firstName, String lastName) {
        Author author = authorRepository.findByFirstNameAndLastName(firstName, lastName);
        if (author == null) {
            author = new Author();
            author.setFirstName(firstName);
            author.setLastName(lastName);
            author = authorRepository.save(author);
            log.debug("Created author: {} {}", firstName, lastName);
        }
        return author;
    }

    private void createBookIfNotExists(String title, String isbn, int year, 
                                     String description, String publisher, Author author) {
        // Check if book already exists by ISBN
        if (bookRepository.findAll().stream()
                .noneMatch(book -> isbn.equals(book.getIsbn()))) {
            
            Book book = new Book();
            book.setTitle(title);
            book.setIsbn(isbn);
            book.setPublicationYear(year);
            book.setDescription(description);
            book.setPublisher(publisher);
            book.setStatus(BookStatus.AVAILABLE);
            
            bookRepository.save(book);
            log.debug("Created book: {}", title);
        }
    }

    @Transactional
    public void generateLargeDataset() {
        log.info("Generating large dataset for load testing...");
        
        try {
            Role studentRole = roleRepository.findByRoleName("STUDENT");
            
            // Generate more users for load testing
            for (int i = 1; i <= 20; i++) {
                String email = "testuser" + i + "@test.com";
                if (userRepository.findByEmail(email) == null) {
                    LibraryUser user = new LibraryUser();
                    user.setUsername("testuser" + i);
                    user.setEmail(email);
                    user.setPasswordHash(passwordEncoder.encode("password123"));
                    
                    if (studentRole != null) {
                        Set<Role> roles = new HashSet<>();
                        roles.add(studentRole);
                        user.setRoles(roles);
                    }
                    
                    userRepository.save(user);
                }
            }
            
            // Generate more books
            for (int i = 1; i <= 50; i++) {
                String isbn = "978-0-123456-" + String.format("%02d", i) + "-0";
                if (bookRepository.findAll().stream()
                        .noneMatch(book -> isbn.equals(book.getIsbn()))) {
                    
                    Book book = new Book();
                    book.setTitle("Test Book " + i);
                    book.setIsbn(isbn);
                    book.setPublicationYear(2020 + (i % 5));
                    book.setDescription("Test book description " + i);
                    book.setPublisher("Test Publisher " + (i % 5));
                    book.setStatus(BookStatus.AVAILABLE);
                    bookRepository.save(book);
                }
            }
            
            log.info("Large dataset generation completed");
        } catch (Exception e) {
            log.error("Error generating large dataset", e);
        }
    }
}