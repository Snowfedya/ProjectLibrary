package org.example.library.monitoring.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.library.monitoring.config.MonitoringProperties;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.RuntimeMXBean;

/**
 * Collector for system-level metrics
 * 
 * Provides JVM and application performance metrics
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SystemMetricsCollector {

    private final MeterRegistry meterRegistry;
    private final MonitoringProperties properties;
    
    private final MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
    private final RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();

    @PostConstruct
    public void initializeSystemMetrics() {
        if (!properties.getPerformance().isMemoryTrackingEnabled()) {
            log.debug("System metrics disabled in configuration");
            return;
        }
        
        String prefix = properties.getMetrics().getPrefix();
        
        // JVM Uptime
        Gauge.builder(prefix + ".system.uptime", this, SystemMetricsCollector::getUptime)
                .description("Application uptime in milliseconds")
                .register(meterRegistry);
        
        // Memory usage percentage
        Gauge.builder(prefix + ".system.memory.usage.ratio", this, SystemMetricsCollector::getMemoryUsageRatio)
                .description("Memory usage ratio (0.0 to 1.0)")
                .register(meterRegistry);
        
        // Available processors
        Gauge.builder(prefix + ".system.cpu.count", this, SystemMetricsCollector::getProcessorCount)
                .description("Number of available processors")
                .register(meterRegistry);
        
        log.info("System metrics initialized successfully");
    }

    /**
     * Get application uptime in milliseconds
     */
    public double getUptime() {
        try {
            return runtimeBean.getUptime();
        } catch (Exception e) {
            log.warn("Error getting uptime", e);
            return 0;
        }
    }

    /**
     * Get memory usage ratio (0.0 to 1.0)
     */
    public double getMemoryUsageRatio() {
        try {
            long used = memoryBean.getHeapMemoryUsage().getUsed();
            long max = memoryBean.getHeapMemoryUsage().getMax();
            return max > 0 ? (double) used / max : 0.0;
        } catch (Exception e) {
            log.warn("Error calculating memory usage", e);
            return 0;
        }
    }

    /**
     * Get number of available processors
     */
    public double getProcessorCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Check if memory usage is above threshold
     */
    public boolean isMemoryUsageHigh() {
        return getMemoryUsageRatio() > 0.85; // 85% threshold
    }

    /**
     * Get formatted uptime string
     */
    public String getFormattedUptime() {
        long uptimeMs = (long) getUptime();
        long seconds = uptimeMs / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        
        return String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60);
    }
}