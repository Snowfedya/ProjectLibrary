package org.example.library.monitoring.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.library.monitoring.config.MonitoringProperties;
import org.example.library.monitoring.metrics.BusinessMetricsCollector;
import org.springframework.stereotype.Component;

/**
 * AOP Aspect for automatic metrics collection
 * 
 * Intercepts method calls and records metrics automatically
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class MetricsAspect {

    private final BusinessMetricsCollector metricsCollector;
    private final MonitoringProperties properties;

    /**
     * Pointcut for all controller methods
     */
    @Pointcut("execution(* org.example.library.controllers..*(..))")
    public void controllerMethods() {}

    /**
     * Pointcut for search methods
     */
    @Pointcut("execution(* org.example.library.controllers.BookController.searchBooks(..))")
    public void searchMethods() {}

    /**
     * Pointcut for user registration
     */
    @Pointcut("execution(* org.example.library.controllers.UserController.registerUser(..))")
    public void registrationMethods() {}

    /**
     * Pointcut for authentication methods
     */
    @Pointcut("execution(* org.example.library.controllers.UserController.login(..))")
    public void authenticationMethods() {}

    /**
     * Around advice for search methods to measure timing and record searches
     */
    @Around("searchMethods()")
    public Object aroundSearchMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!properties.getMetrics().isEnableSearchMetrics()) {
            return joinPoint.proceed();
        }

        var timer = metricsCollector.startSearchTimer();
        try {
            Object result = joinPoint.proceed();
            
            // Record successful search
            metricsCollector.recordBookSearch();
            
            if (timer != null) {
                metricsCollector.stopSearchTimer(timer);
            }
            
            log.debug("Search operation completed and metrics recorded");
            return result;
            
        } catch (Exception e) {
            // Still record the search attempt even if it failed
            metricsCollector.recordBookSearch();
            if (timer != null) {
                metricsCollector.stopSearchTimer(timer);
            }
            throw e;
        }
    }

    /**
     * Around advice for registration methods
     */
    @Around("registrationMethods()")
    public Object aroundRegistrationMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!properties.getMetrics().isEnableRentalMetrics()) {
            return joinPoint.proceed();
        }

        try {
            Object result = joinPoint.proceed();
            
            // Only record if registration was successful
            // Check if the response indicates success
            if (isSuccessfulResponse(result)) {
                metricsCollector.recordUserRegistration();
                log.debug("User registration recorded in metrics");
            }
            
            return result;
            
        } catch (Exception e) {
            log.debug("Registration failed, not recording in metrics");
            throw e;
        }
    }

    /**
     * Around advice for authentication methods to track auth errors
     */
    @Around("authenticationMethods()")
    public Object aroundAuthenticationMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!properties.getMetrics().isEnableSecurityMetrics()) {
            return joinPoint.proceed();
        }

        try {
            Object result = joinPoint.proceed();
            return result;
            
        } catch (Exception e) {
            // Record authentication error
            metricsCollector.recordAuthError();
            log.debug("Authentication error recorded in metrics: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Around advice for slow request detection
     */
    @Around("controllerMethods()")
    public Object aroundControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!properties.getPerformance().isTimingEnabled()) {
            return joinPoint.proceed();
        }

        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;
            
            // Log slow requests
            if (duration > properties.getPerformance().getSlowRequestThresholdMs()) {
                log.warn("Slow request detected: {}ms for method {}", 
                        duration, joinPoint.getSignature().toShortString());
            }
            
            return result;
            
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            log.debug("Request failed after {}ms: {}", duration, joinPoint.getSignature().toShortString());
            throw e;
        }
    }

    /**
     * Helper method to determine if a response indicates success
     */
    private boolean isSuccessfulResponse(Object response) {
        if (response == null) {
            return false;
        }
        
        // Check for ResponseEntity with success status
        if (response.getClass().getSimpleName().equals("ResponseEntity")) {
            try {
                // Use reflection to get status code
                var statusCodeMethod = response.getClass().getMethod("getStatusCode");
                var statusCode = statusCodeMethod.invoke(response);
                return statusCode.toString().startsWith("2"); // 2xx status codes
            } catch (Exception e) {
                log.debug("Could not determine response status, assuming success");
                return true;
            }
        }
        
        // For other response types, assume success if not null
        return true;
    }
}