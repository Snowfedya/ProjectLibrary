package org.example.library.monitoring.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.library.monitoring.metrics.BusinessMetricsCollector;
import org.example.library.monitoring.metrics.SystemMetricsCollector;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Event listener for metrics collection
 * 
 * Processes library events and updates metrics accordingly
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MetricsEventListener {

    private final BusinessMetricsCollector businessMetrics;
    private final SystemMetricsCollector systemMetrics;

    /**
     * Handle application startup
     */
    @EventListener
    public void handleApplicationReady(ApplicationReadyEvent event) {
        log.info("Application ready - monitoring system active");
        log.info("System uptime: {}", systemMetrics.getFormattedUptime());
        log.info("Memory usage: {:.1f}%", systemMetrics.getMemoryUsageRatio() * 100);
    }

    /**
     * Handle user registration events
     */
    @Async
    @EventListener
    public void handleUserRegistration(LibraryMetricsEvents.UserRegistrationEvent event) {
        businessMetrics.recordUserRegistration();
        log.debug("Processed user registration event for user: {}", event.getUsername());
    }

    /**
     * Handle book search events
     */
    @Async
    @EventListener
    public void handleBookSearch(LibraryMetricsEvents.BookSearchEvent event) {
        businessMetrics.recordBookSearch();
        
        if (log.isDebugEnabled()) {
            log.debug("Processed book search event: query='{}', results={}, duration={}ms", 
                    event.getSearchQuery(), event.getResultsCount(), event.getSearchDurationMs());
        }
    }

    /**
     * Handle book borrow events
     */
    @Async
    @EventListener
    public void handleBookBorrow(LibraryMetricsEvents.BookBorrowEvent event) {
        businessMetrics.recordBookBorrow();
        log.debug("Processed book borrow event: book='{}', user='{}'", 
                event.getBookTitle(), event.getUsername());
    }

    /**
     * Handle book return events
     */
    @Async
    @EventListener
    public void handleBookReturn(LibraryMetricsEvents.BookReturnEvent event) {
        businessMetrics.recordBookReturn();
        
        if (event.getDaysLate() > 0) {
            log.info("Book returned late: '{}' by '{}' - {} days late", 
                    event.getBookTitle(), event.getUsername(), event.getDaysLate());
        }
        
        log.debug("Processed book return event: book='{}', onTime={}", 
                event.getBookTitle(), event.isOnTime());
    }

    /**
     * Handle authentication error events
     */
    @Async
    @EventListener
    public void handleAuthenticationError(LibraryMetricsEvents.AuthenticationErrorEvent event) {
        businessMetrics.recordAuthError();
        
        log.warn("Authentication error recorded: type='{}', user='{}', endpoint='{}', IP='{}'",
                event.getErrorType(), event.getUsername(), event.getEndpoint(), event.getIpAddress());
    }

    /**
     * Handle system performance events
     */
    @Async
    @EventListener
    public void handleSystemPerformance(LibraryMetricsEvents.SystemPerformanceEvent event) {
        log.warn("System performance alert: {} - {} (value: {}, threshold: {})",
                event.getAlertType(), event.getMessage(), event.getValue(), event.getThreshold());
        
        // Additional actions could be taken here like sending notifications
        if ("HIGH_MEMORY_USAGE".equals(event.getAlertType())) {
            log.error("Critical: Memory usage is at {:.1f}% - consider scaling up", 
                    event.getValue() * 100);
        }
    }
}