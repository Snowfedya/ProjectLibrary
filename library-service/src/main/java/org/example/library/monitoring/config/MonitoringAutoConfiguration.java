package org.example.library.monitoring.config;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.library.monitoring.aspects.MetricsAspect;
import org.example.library.monitoring.metrics.BusinessMetricsCollector;
import org.example.library.monitoring.metrics.SystemMetricsCollector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Auto-configuration for Library Monitoring System
 * 
 * Provides zero-configuration setup with sensible defaults
 */
@Slf4j
@Configuration
@EnableAspectJAutoProxy
@RequiredArgsConstructor
@EnableConfigurationProperties(MonitoringProperties.class)
@ConditionalOnProperty(
    prefix = "library.monitoring.metrics", 
    name = "enabled", 
    havingValue = "true", 
    matchIfMissing = true
)
public class MonitoringAutoConfiguration {

    private final MonitoringProperties properties;

    /**
     * Registry customizer for common tags
     */
    @Bean
    public org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer<MeterRegistry> libraryMeterRegistryCustomizer() {
        return registry -> {
            registry.config()
                    .commonTags(
                        "application", properties.getApplication().getName(),
                        "version", properties.getApplication().getVersion(),
                        "environment", properties.getApplication().getEnvironment()
                    );
            log.info("Configured meter registry with common tags for {}", 
                    properties.getApplication().getName());
        };
    }

    /**
     * Business metrics collector for library-specific metrics
     */
    @Bean
    @ConditionalOnProperty(
        prefix = "library.monitoring.metrics", 
        name = "enabled", 
        havingValue = "true"
    )
    public BusinessMetricsCollector businessMetricsCollector(MeterRegistry meterRegistry) {
        log.info("Creating BusinessMetricsCollector with prefix: {}", 
                properties.getMetrics().getPrefix());
        return new BusinessMetricsCollector(meterRegistry, properties);
    }

    /**
     * System metrics collector for JVM and application metrics
     */
    @Bean
    @ConditionalOnProperty(
        prefix = "library.monitoring.performance", 
        name = "memoryTrackingEnabled", 
        havingValue = "true",
        matchIfMissing = true
    )
    public SystemMetricsCollector systemMetricsCollector(MeterRegistry meterRegistry) {
        log.info("Creating SystemMetricsCollector for performance monitoring");
        return new SystemMetricsCollector(meterRegistry, properties);
    }

    /**
     * AOP aspect for automatic metrics collection
     */
    @Bean
    @ConditionalOnProperty(
        prefix = "library.monitoring.performance", 
        name = "timingEnabled", 
        havingValue = "true",
        matchIfMissing = true
    )
    public MetricsAspect metricsAspect(BusinessMetricsCollector businessMetrics) {
        log.info("Creating MetricsAspect for automatic timing and counting");
        return new MetricsAspect(businessMetrics, properties);
    }
}