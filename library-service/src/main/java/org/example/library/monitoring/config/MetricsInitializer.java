package org.example.library.monitoring.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.library.monitoring.metrics.BusinessMetricsCollector;
import org.example.library.repositories.BookRepository;
import org.example.library.repositories.LibraryUserRepository;
import org.example.library.repositories.RentalRequestRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Initializes metrics after application startup
 * 
 * Ensures repositories are ready before gauge metrics are created
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MetricsInitializer {

    private final BusinessMetricsCollector businessMetrics;
    private final BookRepository bookRepository;
    private final LibraryUserRepository userRepository;  
    private final RentalRequestRepository rentalRequestRepository;

    /**
     * Initialize gauge metrics after application is fully started
     */
    @EventListener
    public void initializeGaugeMetrics(ApplicationReadyEvent event) {
        try {
            businessMetrics.initializeGauges(bookRepository, userRepository, rentalRequestRepository);
            log.info("✅ Library monitoring system initialized successfully");
            log.info("📊 Metrics available at: /actuator/prometheus");
            log.info("🏥 Health check at: /actuator/health");
        } catch (Exception e) {
            log.error("❌ Failed to initialize gauge metrics", e);
        }
    }
}