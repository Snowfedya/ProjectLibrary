package org.example.library.monitoring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * Configuration properties for library monitoring system
 * 
 * Provides type-safe configuration with validation and defaults
 */
@Data
@Validated
@ConfigurationProperties(prefix = "library.monitoring")
public class MonitoringProperties {

    /**
     * Application metadata for metrics tagging
     */
    private Application application = new Application();
    
    /**
     * Metrics collection settings
     */
    private Metrics metrics = new Metrics();
    
    /**
     * Performance monitoring settings
     */
    private Performance performance = new Performance();

    @Data
    public static class Application {
        /**
         * Application name for metric tagging
         */
        @NotBlank
        private String name = "library-service";
        
        /**
         * Application version for metric tagging
         */
        @NotBlank
        private String version = "1.0.0-SNAPSHOT";
        
        /**
         * Environment (dev, staging, prod)
         */
        private String environment = "development";
    }

    @Data
    public static class Metrics {
        /**
         * Whether to enable custom business metrics
         */
        private boolean enabled = true;
        
        /**
         * Prefix for all custom metrics
         */
        @NotBlank
        private String prefix = "library";
        
        /**
         * Whether to enable detailed search metrics
         */
        private boolean enableSearchMetrics = true;
        
        /**
         * Whether to enable authentication error tracking
         */
        private boolean enableSecurityMetrics = true;
        
        /**
         * Whether to enable rental/borrowing metrics
         */
        private boolean enableRentalMetrics = true;
    }

    @Data
    public static class Performance {
        /**
         * Whether to enable timing metrics
         */
        private boolean timingEnabled = true;
        
        /**
         * Slow request threshold in milliseconds
         */
        @Positive
        private long slowRequestThresholdMs = 1000L;
        
        /**
         * Whether to track memory usage metrics
         */
        private boolean memoryTrackingEnabled = true;
    }
}