package org.example.library.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;

/**
 * Конфигурация метрик для мониторинга библиотеки
 * Настраивает кастомные метрики для Prometheus/Grafana
 */
@Configuration
public class MetricsConfig {

    /**
     * Настройка общих тегов для всех метрик
     */
    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config()
                .commonTags("application", "library-service")
                .commonTags("version", "1.0.0-SNAPSHOT");
    }

    /**
     * Счетчик регистраций пользователей
     */
    @Bean
    public Counter userRegistrationCounter(MeterRegistry registry) {
        return Counter.builder("library.users.registrations.total")
                .description("Total number of user registrations")
                .register(registry);
    }

    /**
     * Счетчик займов книг
     */
    @Bean
    public Counter bookBorrowCounter(MeterRegistry registry) {
        return Counter.builder("library.books.borrowed.total")
                .description("Total number of books borrowed")
                .register(registry);
    }

    /**
     * Счетчик возвратов книг
     */
    @Bean
    public Counter bookReturnCounter(MeterRegistry registry) {
        return Counter.builder("library.books.returned.total")
                .description("Total number of books returned")
                .register(registry);
    }

    /**
     * Счетчик поиска книг
     */
    @Bean
    public Counter bookSearchCounter(MeterRegistry registry) {
        return Counter.builder("library.books.searches.total")
                .description("Total number of book searches")
                .register(registry);
    }

    /**
     * Счетчик ошибок аутентификации
     */
    @Bean
    public Counter authenticationErrorCounter(MeterRegistry registry) {
        return Counter.builder("library.auth.errors.total")
                .description("Total number of authentication errors")
                .register(registry);
    }

    /**
     * Таймер для измерения времени поиска книг
     */
    @Bean
    public Timer bookSearchTimer(MeterRegistry registry) {
        return Timer.builder("library.books.search.duration")
                .description("Time taken to search books")
                .register(registry);
    }

    /**
     * Таймер для измерения времени создания займа
     */
    @Bean
    public Timer bookBorrowTimer(MeterRegistry registry) {
        return Timer.builder("library.books.borrow.duration")
                .description("Time taken to borrow a book")
                .register(registry);
    }
}