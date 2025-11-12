package org.example.library.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.library.services.MetricsService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Инициализация метрик при старте приложения
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class MetricsInitializer {

    private final MetricsService metricsService;

    /**
     * Инициализация Gauge метрик после запуска приложения
     */
    @Bean
    public ApplicationRunner initializeMetrics() {
        return args -> {
            log.info("Initializing metrics gauges...");
            metricsService.initializeGauges();
            log.info("Metrics initialization completed successfully!");
        };
    }
}