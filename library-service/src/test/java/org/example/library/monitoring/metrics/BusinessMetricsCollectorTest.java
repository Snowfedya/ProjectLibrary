package org.example.library.monitoring.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.example.library.monitoring.config.MonitoringProperties;
import org.example.library.models.BookStatus;
import org.example.library.models.RentalRequestStatus;
import org.example.library.repositories.BookRepository;
import org.example.library.repositories.LibraryUserRepository;
import org.example.library.repositories.RentalRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Unit tests for BusinessMetricsCollector
 */
@ExtendWith(MockitoExtension.class)
class BusinessMetricsCollectorTest {

    @Mock
    private BookRepository bookRepository;
    
    @Mock
    private LibraryUserRepository userRepository;
    
    @Mock
    private RentalRequestRepository rentalRequestRepository;

    private MeterRegistry meterRegistry;
    private MonitoringProperties properties;
    private BusinessMetricsCollector metricsCollector;

    @BeforeEach
    void setUp() {
        meterRegistry = new SimpleMeterRegistry();
        properties = createDefaultProperties();
        metricsCollector = new BusinessMetricsCollector(meterRegistry, properties);
        metricsCollector.initializeMetrics();
    }

    @Test
    void shouldRecordUserRegistration() {
        // When
        metricsCollector.recordUserRegistration();

        // Then
        var counter = meterRegistry.find("library.users.registrations.total").counter();
        assertThat(counter).isNotNull();
        assertThat(counter.count()).isEqualTo(1.0);
    }

    @Test
    void shouldRecordBookSearch() {
        // When
        metricsCollector.recordBookSearch();
        metricsCollector.recordBookSearch();

        // Then
        var counter = meterRegistry.find("library.books.searches.total").counter();
        assertThat(counter).isNotNull();
        assertThat(counter.count()).isEqualTo(2.0);
    }

    @Test
    void shouldCalculateTotalBooksGauge() {
        // Given
        when(bookRepository.count()).thenReturn(42L);
        metricsCollector.initializeGauges(bookRepository, userRepository, rentalRequestRepository);

        // When
        var gauge = meterRegistry.find("library.books.total").gauge();

        // Then
        assertThat(gauge).isNotNull();
        assertThat(gauge.value()).isEqualTo(42.0);
    }

    @Test
    void shouldCalculateActiveRentalsGauge() {
        // Given
        when(rentalRequestRepository.findByStatus(RentalRequestStatus.APPROVED))
                .thenReturn(Collections.nCopies(5, null));
        metricsCollector.initializeGauges(bookRepository, userRepository, rentalRequestRepository);

        // When
        var gauge = meterRegistry.find("library.rentals.active").gauge();

        // Then
        assertThat(gauge).isNotNull();
        assertThat(gauge.value()).isEqualTo(5.0);
    }

    @Test
    void shouldHandleTimingOperations() {
        // Given
        var startSample = metricsCollector.startSearchTimer();

        // When
        try {
            Thread.sleep(10); // Small delay for timing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        metricsCollector.stopSearchTimer(startSample);

        // Then
        var timer = meterRegistry.find("library.books.search.duration").timer();
        assertThat(timer).isNotNull();
        assertThat(timer.count()).isEqualTo(1L);
        assertThat(timer.totalTime(java.util.concurrent.TimeUnit.MILLISECONDS)).isPositive();
    }

    @Test
    void shouldHandleNullTimerSample() {
        // When/Then - should not throw exception
        metricsCollector.stopSearchTimer(null);
    }

    @Test
    void shouldDisableMetricsWhenConfigured() {
        // Given
        var disabledProperties = createDefaultProperties();
        disabledProperties.getMetrics().setEnableSearchMetrics(false);
        
        var disabledCollector = new BusinessMetricsCollector(meterRegistry, disabledProperties);
        disabledCollector.initializeMetrics();

        // When
        disabledCollector.recordBookSearch();

        // Then
        var counter = meterRegistry.find("library.books.searches.total").counter();
        assertThat(counter).isNull();
    }

    private MonitoringProperties createDefaultProperties() {
        var props = new MonitoringProperties();
        props.getMetrics().setEnabled(true);
        props.getMetrics().setPrefix("library");
        props.getMetrics().setEnableSearchMetrics(true);
        props.getMetrics().setEnableSecurityMetrics(true);
        props.getMetrics().setEnableRentalMetrics(true);
        props.getPerformance().setTimingEnabled(true);
        return props;
    }
}