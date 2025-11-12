package org.example.library.monitoring;

import org.example.library.monitoring.events.LibraryMetricsEvents;
import org.example.library.monitoring.metrics.BusinessMetricsCollector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for LibraryMetrics facade
 */
@ExtendWith(MockitoExtension.class)
class LibraryMetricsTest {

    @Mock
    private ApplicationEventPublisher eventPublisher;
    
    @Mock
    private BusinessMetricsCollector businessMetrics;

    private LibraryMetrics libraryMetrics;

    @BeforeEach
    void setUp() {
        libraryMetrics = new LibraryMetrics(eventPublisher, businessMetrics);
    }

    @Test
    void shouldPublishUserRegistrationEvent() {
        // Given
        String username = "testuser";
        String email = "test@example.com";
        String role = "READER";

        // When
        libraryMetrics.recordUserRegistration(username, email, role);

        // Then
        ArgumentCaptor<LibraryMetricsEvents.UserRegistrationEvent> captor = 
                ArgumentCaptor.forClass(LibraryMetricsEvents.UserRegistrationEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());

        LibraryMetricsEvents.UserRegistrationEvent event = captor.getValue();
        assertThat(event.getUsername()).isEqualTo(username);
        assertThat(event.getEmail()).isEqualTo(email);
        assertThat(event.getRole()).isEqualTo(role);
        assertThat(event.getEventType()).isEqualTo("USER_REGISTRATION");
    }

    @Test
    void shouldPublishBookSearchEvent() {
        // Given
        String query = "Spring Boot";
        int resultsCount = 5;
        long durationMs = 150L;
        String searchType = "query";

        // When
        libraryMetrics.recordBookSearch(query, resultsCount, durationMs, searchType);

        // Then
        ArgumentCaptor<LibraryMetricsEvents.BookSearchEvent> captor = 
                ArgumentCaptor.forClass(LibraryMetricsEvents.BookSearchEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());

        LibraryMetricsEvents.BookSearchEvent event = captor.getValue();
        assertThat(event.getSearchQuery()).isEqualTo(query);
        assertThat(event.getResultsCount()).isEqualTo(resultsCount);
        assertThat(event.getSearchDurationMs()).isEqualTo(durationMs);
        assertThat(event.getSearchType()).isEqualTo(searchType);
        assertThat(event.getEventType()).isEqualTo("BOOK_SEARCH");
    }

    @Test
    void shouldPublishAuthErrorEvent() {
        // Given
        String username = "baduser";
        String errorType = "invalid_password";
        String endpoint = "/api/users/login";
        String ipAddress = "192.168.1.1";

        // When
        libraryMetrics.recordAuthError(username, errorType, endpoint, ipAddress);

        // Then
        ArgumentCaptor<LibraryMetricsEvents.AuthenticationErrorEvent> captor = 
                ArgumentCaptor.forClass(LibraryMetricsEvents.AuthenticationErrorEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());

        LibraryMetricsEvents.AuthenticationErrorEvent event = captor.getValue();
        assertThat(event.getUsername()).isEqualTo(username);
        assertThat(event.getErrorType()).isEqualTo(errorType);
        assertThat(event.getEndpoint()).isEqualTo(endpoint);
        assertThat(event.getIpAddress()).isEqualTo(ipAddress);
        assertThat(event.getEventType()).isEqualTo("AUTH_ERROR");
    }

    @Test
    void shouldHandleTimingContext() {
        // When
        LibraryMetrics.TimingContext context = libraryMetrics.startTiming();
        
        // Then
        assertThat(context).isNotNull();
        assertThat(context.getStartTime()).isPositive();
        assertThat(context.getDurationMs()).isNotNegative();
    }

    @Test
    void shouldHandleExceptionsGracefully() {
        // Given
        doThrow(new RuntimeException("Event publishing failed"))
                .when(eventPublisher).publishEvent(any());

        // When/Then - should not throw exception
        libraryMetrics.recordUserRegistration("test", "test@example.com", "READER");
        libraryMetrics.recordBookSearch("query", 0, 0L, "test");
        libraryMetrics.recordAuthError("user", "error", "endpoint", "ip");
    }

    @Test
    void timingContextShouldMeasureElapsedTime() throws InterruptedException {
        // Given
        LibraryMetrics.TimingContext context = libraryMetrics.startTiming();
        long startTime = context.getStartTime();

        // When
        Thread.sleep(10);
        long duration = context.getDurationMs();

        // Then
        assertThat(duration).isGreaterThan(0);
        assertThat(context.getStartTime()).isEqualTo(startTime); // Should remain constant
    }
}