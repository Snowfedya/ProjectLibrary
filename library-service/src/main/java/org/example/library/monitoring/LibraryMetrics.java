package org.example.library.monitoring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.library.monitoring.events.LibraryMetricsEvents;
import org.example.library.monitoring.metrics.BusinessMetricsCollector;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Simplified facade for library metrics collection
 * 
 * Provides a clean API for controllers and services to record metrics
 * without tight coupling to the monitoring implementation
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LibraryMetrics {

    private final ApplicationEventPublisher eventPublisher;
    private final BusinessMetricsCollector businessMetrics;

    /**
     * Record a user registration
     */
    public void recordUserRegistration(String username, String email, String role) {
        try {
            eventPublisher.publishEvent(
                new LibraryMetricsEvents.UserRegistrationEvent(this, username, email, role)
            );
        } catch (Exception e) {
            log.warn("Failed to record user registration metric", e);
        }
    }

    /**
     * Record a book search
     */
    public void recordBookSearch(String searchQuery, int resultsCount, long durationMs, String searchType) {
        try {
            eventPublisher.publishEvent(
                new LibraryMetricsEvents.BookSearchEvent(this, searchQuery, resultsCount, durationMs, searchType)
            );
        } catch (Exception e) {
            log.warn("Failed to record book search metric", e);
        }
    }

    /**
     * Record a book borrow
     */
    public void recordBookBorrow(Long bookId, String bookTitle, String username, String borrowType) {
        try {
            eventPublisher.publishEvent(
                new LibraryMetricsEvents.BookBorrowEvent(this, bookId, bookTitle, username, borrowType)
            );
        } catch (Exception e) {
            log.warn("Failed to record book borrow metric", e);
        }
    }

    /**
     * Record a book return
     */
    public void recordBookReturn(Long bookId, String bookTitle, String username, boolean onTime, int daysLate) {
        try {
            eventPublisher.publishEvent(
                new LibraryMetricsEvents.BookReturnEvent(this, bookId, bookTitle, username, onTime, daysLate)
            );
        } catch (Exception e) {
            log.warn("Failed to record book return metric", e);
        }
    }

    /**
     * Record an authentication error
     */
    public void recordAuthError(String username, String errorType, String endpoint, String ipAddress) {
        try {
            eventPublisher.publishEvent(
                new LibraryMetricsEvents.AuthenticationErrorEvent(this, username, errorType, endpoint, ipAddress)
            );
        } catch (Exception e) {
            log.warn("Failed to record authentication error metric", e);
        }
    }

    /**
     * Record a system performance alert
     */
    public void recordSystemAlert(String alertType, String message, double value, double threshold) {
        try {
            eventPublisher.publishEvent(
                new LibraryMetricsEvents.SystemPerformanceEvent(this, alertType, message, value, threshold)
            );
        } catch (Exception e) {
            log.warn("Failed to record system performance metric", e);
        }
    }

    /**
     * Start timing an operation
     */
    public TimingContext startTiming() {
        return new TimingContext(System.currentTimeMillis());
    }

    /**
     * Context for timing operations
     */
    public static class TimingContext {
        private final long startTime;

        private TimingContext(long startTime) {
            this.startTime = startTime;
        }

        public long getDurationMs() {
            return System.currentTimeMillis() - startTime;
        }

        public long getStartTime() {
            return startTime;
        }
    }
}