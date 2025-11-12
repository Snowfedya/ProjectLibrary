package org.example.library.monitoring.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;

/**
 * Application events for metrics collection
 * 
 * Provides loose coupling between business logic and metrics collection
 */
public class LibraryMetricsEvents {

    /**
     * Base class for all library metrics events
     */
    @Getter
    public static abstract class BaseLibraryEvent extends ApplicationEvent {
        private final long eventTimestamp;
        private final String eventType;

        public BaseLibraryEvent(Object source, String eventType) {
            super(source);
            this.eventTimestamp = System.currentTimeMillis();
            this.eventType = eventType;
        }
    }

    /**
     * Event fired when a user registers
     */
    @Getter
    public static class UserRegistrationEvent extends BaseLibraryEvent {
        private final String username;
        private final String email;
        private final String role;

        public UserRegistrationEvent(Object source, String username, String email, String role) {
            super(source, "USER_REGISTRATION");
            this.username = username;
            this.email = email;
            this.role = role;
        }
    }

    /**
     * Event fired when a book search is performed
     */
    @Getter
    public static class BookSearchEvent extends BaseLibraryEvent {
        private final String searchQuery;
        private final int resultsCount;
        private final long searchDurationMs;
        private final String searchType;

        public BookSearchEvent(Object source, String searchQuery, int resultsCount, 
                             long searchDurationMs, String searchType) {
            super(source, "BOOK_SEARCH");
            this.searchQuery = searchQuery;
            this.resultsCount = resultsCount;
            this.searchDurationMs = searchDurationMs;
            this.searchType = searchType;
        }
    }

    /**
     * Event fired when a book is borrowed
     */
    @Getter
    public static class BookBorrowEvent extends BaseLibraryEvent {
        private final Long bookId;
        private final String bookTitle;
        private final String username;
        private final String borrowType;

        public BookBorrowEvent(Object source, Long bookId, String bookTitle, 
                             String username, String borrowType) {
            super(source, "BOOK_BORROW");
            this.bookId = bookId;
            this.bookTitle = bookTitle;
            this.username = username;
            this.borrowType = borrowType;
        }
    }

    /**
     * Event fired when a book is returned
     */
    @Getter
    public static class BookReturnEvent extends BaseLibraryEvent {
        private final Long bookId;
        private final String bookTitle;
        private final String username;
        private final boolean onTime;
        private final int daysLate;

        public BookReturnEvent(Object source, Long bookId, String bookTitle, 
                             String username, boolean onTime, int daysLate) {
            super(source, "BOOK_RETURN");
            this.bookId = bookId;
            this.bookTitle = bookTitle;
            this.username = username;
            this.onTime = onTime;
            this.daysLate = daysLate;
        }
    }

    /**
     * Event fired when an authentication error occurs
     */
    @Getter
    public static class AuthenticationErrorEvent extends BaseLibraryEvent {
        private final String username;
        private final String errorType;
        private final String endpoint;
        private final String ipAddress;

        public AuthenticationErrorEvent(Object source, String username, String errorType, 
                                      String endpoint, String ipAddress) {
            super(source, "AUTH_ERROR");
            this.username = username;
            this.errorType = errorType;
            this.endpoint = endpoint;
            this.ipAddress = ipAddress;
        }
    }

    /**
     * Event fired for system performance alerts
     */
    @Getter
    public static class SystemPerformanceEvent extends BaseLibraryEvent {
        private final String alertType;
        private final String message;
        private final double value;
        private final double threshold;

        public SystemPerformanceEvent(Object source, String alertType, String message, 
                                    double value, double threshold) {
            super(source, "SYSTEM_PERFORMANCE");
            this.alertType = alertType;
            this.message = message;
            this.value = value;
            this.threshold = threshold;
        }
    }
}