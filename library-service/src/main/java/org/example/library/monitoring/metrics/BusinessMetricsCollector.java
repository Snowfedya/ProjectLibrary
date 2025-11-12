package org.example.library.monitoring.metrics;

import io.micrometer.core.instrument.*;
import lombok.extern.slf4j.Slf4j;
import org.example.library.monitoring.config.MonitoringProperties;
import org.example.library.repositories.BookRepository;
import org.example.library.repositories.LibraryUserRepository;
import org.example.library.repositories.RentalRequestRepository;
import org.example.library.models.RentalRequestStatus;
import org.example.library.models.BookStatus;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * Collector for business-specific metrics
 * 
 * Handles library domain metrics like books, users, rentals
 */
@Slf4j
@Component
public class BusinessMetricsCollector {

    private final MeterRegistry meterRegistry;
    private final MonitoringProperties properties;
    
    // Repositories for gauge calculations
    private BookRepository bookRepository;
    private LibraryUserRepository userRepository;
    private RentalRequestRepository rentalRequestRepository;
    
    // Counter metrics
    private Counter userRegistrationCounter;
    private Counter bookSearchCounter;
    private Counter bookBorrowCounter;
    private Counter bookReturnCounter;
    private Counter authErrorCounter;
    
    // Timer metrics
    private Timer searchTimer;
    private Timer borrowTimer;

    public BusinessMetricsCollector(MeterRegistry meterRegistry, MonitoringProperties properties) {
        this.meterRegistry = meterRegistry;
        this.properties = properties;
    }

    /**
     * Initialize metrics after dependencies are injected
     */
    @PostConstruct
    public void initializeMetrics() {
        String prefix = properties.getMetrics().getPrefix();
        
        // Initialize counters
        if (properties.getMetrics().isEnableRentalMetrics()) {
            userRegistrationCounter = Counter.builder(prefix + ".users.registrations.total")
                    .description("Total number of user registrations")
                    .register(meterRegistry);
                    
            bookBorrowCounter = Counter.builder(prefix + ".books.borrowed.total")
                    .description("Total number of books borrowed")
                    .register(meterRegistry);
                    
            bookReturnCounter = Counter.builder(prefix + ".books.returned.total")
                    .description("Total number of books returned")
                    .register(meterRegistry);
        }
        
        if (properties.getMetrics().isEnableSearchMetrics()) {
            bookSearchCounter = Counter.builder(prefix + ".books.searches.total")
                    .description("Total number of book searches")
                    .register(meterRegistry);
        }
        
        if (properties.getMetrics().isEnableSecurityMetrics()) {
            authErrorCounter = Counter.builder(prefix + ".auth.errors.total")
                    .description("Total number of authentication errors")
                    .register(meterRegistry);
        }
        
        // Initialize timers
        if (properties.getPerformance().isTimingEnabled()) {
            searchTimer = Timer.builder(prefix + ".books.search.duration")
                    .description("Time taken to search books")
                    .register(meterRegistry);
                    
            borrowTimer = Timer.builder(prefix + ".books.borrow.duration")
                    .description("Time taken to borrow a book")
                    .register(meterRegistry);
        }
        
        log.info("BusinessMetricsCollector initialized with {} metrics", 
                meterRegistry.getMeters().size());
    }

    /**
     * Initialize gauge metrics (called after repositories are available)
     */
    public void initializeGauges(BookRepository bookRepo, 
                                LibraryUserRepository userRepo, 
                                RentalRequestRepository rentalRepo) {
        this.bookRepository = bookRepo;
        this.userRepository = userRepo;
        this.rentalRequestRepository = rentalRepo;
        
        String prefix = properties.getMetrics().getPrefix();
        
        // Books metrics
        Gauge.builder(prefix + ".books.total", this, BusinessMetricsCollector::getTotalBooks)
                .description("Total number of books in library")
                .register(meterRegistry);
                
        Gauge.builder(prefix + ".books.available", this, BusinessMetricsCollector::getAvailableBooks)
                .description("Number of available books")
                .register(meterRegistry);
        
        // Users metrics        
        Gauge.builder(prefix + ".users.total", this, BusinessMetricsCollector::getTotalUsers)
                .description("Total number of registered users")
                .register(meterRegistry);
        
        // Rentals metrics
        Gauge.builder(prefix + ".rentals.active", this, BusinessMetricsCollector::getActiveRentals)
                .description("Number of active book rentals")
                .register(meterRegistry);
                
        Gauge.builder(prefix + ".rentals.overdue", this, BusinessMetricsCollector::getOverdueRentals)
                .description("Number of overdue book rentals")
                .register(meterRegistry);
        
        log.info("Business gauge metrics initialized successfully");
    }

    // Public API methods for recording events
    
    /**
     * Record a user registration event
     */
    public void recordUserRegistration() {
        if (userRegistrationCounter != null) {
            userRegistrationCounter.increment();
            log.debug("User registration recorded");
        }
    }
    
    /**
     * Record a book search event
     */
    public void recordBookSearch() {
        if (bookSearchCounter != null) {
            bookSearchCounter.increment();
            log.debug("Book search recorded");
        }
    }
    
    /**
     * Record a book borrow event
     */
    public void recordBookBorrow() {
        if (bookBorrowCounter != null) {
            bookBorrowCounter.increment();
            log.debug("Book borrow recorded");
        }
    }
    
    /**
     * Record a book return event
     */
    public void recordBookReturn() {
        if (bookReturnCounter != null) {
            bookReturnCounter.increment();
            log.debug("Book return recorded");
        }
    }
    
    /**
     * Record an authentication error
     */
    public void recordAuthError() {
        if (authErrorCounter != null) {
            authErrorCounter.increment();
            log.debug("Authentication error recorded");
        }
    }
    
    /**
     * Time a search operation
     */
    public Timer.Sample startSearchTimer() {
        return searchTimer != null ? Timer.start(meterRegistry) : null;
    }
    
    /**
     * Complete search timing
     */
    public void stopSearchTimer(Timer.Sample sample) {
        if (sample != null && searchTimer != null) {
            sample.stop(searchTimer);
        }
    }
    
    /**
     * Time a borrow operation
     */
    public Timer.Sample startBorrowTimer() {
        return borrowTimer != null ? Timer.start(meterRegistry) : null;
    }
    
    /**
     * Complete borrow timing
     */
    public void stopBorrowTimer(Timer.Sample sample) {
        if (sample != null && borrowTimer != null) {
            sample.stop(borrowTimer);
        }
    }

    // Private gauge calculation methods
    
    private double getTotalBooks() {
        try {
            return bookRepository != null ? bookRepository.count() : 0;
        } catch (Exception e) {
            log.warn("Error calculating total books", e);
            return 0;
        }
    }
    
    private double getAvailableBooks() {
        try {
            return bookRepository != null ? 
                bookRepository.findRentedBooks(BookStatus.AVAILABLE).size() : 0;
        } catch (Exception e) {
            log.warn("Error calculating available books", e);
            return 0;
        }
    }
    
    private double getTotalUsers() {
        try {
            return userRepository != null ? userRepository.count() : 0;
        } catch (Exception e) {
            log.warn("Error calculating total users", e);
            return 0;
        }
    }
    
    private double getActiveRentals() {
        try {
            return rentalRequestRepository != null ? 
                rentalRequestRepository.findByStatus(RentalRequestStatus.APPROVED).size() : 0;
        } catch (Exception e) {
            log.warn("Error calculating active rentals", e);
            return 0;
        }
    }
    
    private double getOverdueRentals() {
        // TODO: Implement overdue logic when due dates are added
        return 0;
    }
}