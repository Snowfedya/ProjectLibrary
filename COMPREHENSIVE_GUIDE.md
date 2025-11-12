# 📚 COMPREHENSIVE GUIDE: Library Management System

## 🎯 OVERVIEW

Library Management System - это современное Spring Boot 3.x приложение с полноценной системой мониторинга, созданное с использованием лучших практик и clean architecture.

### ✨ **Ключевые особенности:**
- **🏗️ Clean Architecture** - модульная структура с разделением ответственности
- **📊 Comprehensive Monitoring** - Grafana + Prometheus + custom metrics
- **🔧 Zero Configuration** - автоконфигурация с разумными defaults
- **⚡ High Performance** - AOP-based metrics, async processing
- **🧪 Production Ready** - полное тестирование, health checks

---

## 🚀 QUICK START

### 1. **Запуск приложения (Demo режим)**
```bash
# Запуск с H2 in-memory базой
cd library-service
mvn spring-boot:run -Dspring-boot.run.profiles=demo

# Приложение доступно на http://localhost:8081
```

### 2. **Запуск с мониторингом**
```bash
# Запуск Prometheus + Grafana
docker-compose -f docker-compose-monitoring.yaml up -d

# Проверка метрик
curl http://localhost:8081/actuator/prometheus | grep library_
```

### 3. **Доступ к сервисам**
| Сервис | URL | Credentials |
|--------|-----|-------------|
| **Library App** | http://localhost:8081 | - |
| **Health Check** | http://localhost:8081/actuator/health | - |
| **Metrics** | http://localhost:8081/actuator/prometheus | - |
| **Grafana** | http://localhost:3000 | admin/admin123 |
| **Prometheus** | http://localhost:9090 | - |

---

## 🏗️ ARCHITECTURE OVERVIEW

### **Модульная структура:**
```
library-service/src/main/java/org/example/library/
├── 📊 monitoring/                    # Система мониторинга
│   ├── config/                       # Автоконфигурация
│   │   ├── MonitoringAutoConfiguration.java
│   │   ├── MonitoringProperties.java
│   │   └── MetricsInitializer.java
│   ├── metrics/                      # Сборщики метрик
│   │   ├── BusinessMetricsCollector.java
│   │   └── SystemMetricsCollector.java
│   ├── aspects/                      # AOP для автометрик
│   │   └── MetricsAspect.java
│   ├── events/                       # Event-driven архитектура
│   │   ├── LibraryMetricsEvents.java
│   │   └── MetricsEventListener.java
│   └── LibraryMetrics.java          # Simplified facade
├── 🎮 controllers/                   # REST Controllers
├── 🔧 services/                      # Business Logic
├── 📁 repositories/                  # Data Access
├── 🏛️ models/                        # Domain Models
└── ⚙️ config/                       # App Configuration
```

---

## 📊 MONITORING SYSTEM

### **Custom Business Metrics:**

#### **Counters** (увеличиваются со временем):
- `library.users.registrations.total` - Общее количество регистраций
- `library.books.searches.total` - Общее количество поисков книг
- `library.books.borrowed.total` - Общее количество займов
- `library.books.returned.total` - Общее количество возвратов
- `library.auth.errors.total` - Ошибки аутентификации

#### **Gauges** (текущее состояние):
- `library.books.total` - Книг в библиотеке
- `library.users.total` - Зарегистрированных пользователей
- `library.rentals.active` - Активных займов
- `library.rentals.overdue` - Просроченных займов

#### **Timers** (время выполнения):
- `library.books.search.duration` - Время поиска книг
- `library.books.borrow.duration` - Время оформления займа

#### **System Metrics:**
- `library.system.uptime` - Время работы приложения
- `library.system.memory.usage.ratio` - Использование памяти JVM
- `library.system.cpu.count` - Количество процессоров

### **Automatic Metrics Collection:**

```java
// ✅ Автоматически через AOP
@GetMapping("/search")
public ResponseEntity<Page<BookDTO>> searchBooks(...) {
    // Метрики записываются автоматически через MetricsAspect
}

// ✅ Простой API для ручной записи
@Autowired
private LibraryMetrics metrics;

metrics.recordUserRegistration("username", "email", "READER");
metrics.recordBookSearch("query", 5, 150L, "search_type");
```

---

## ⚙️ CONFIGURATION

### **Application Properties:**

#### **Core Configuration** (`application-demo.properties`):
```properties
# Database
spring.datasource.url=jdbc:h2:mem:librarydb
spring.jpa.hibernate.ddl-auto=create-drop

# Monitoring import
spring.config.import=optional:classpath:application-monitoring.properties
```

#### **Monitoring Configuration** (`application-monitoring.properties`):
```properties
# Application metadata
library.monitoring.application.name=library-service
library.monitoring.application.environment=development

# Metrics settings
library.monitoring.metrics.enabled=true
library.monitoring.metrics.prefix=library
library.monitoring.metrics.enable-search-metrics=true

# Performance settings
library.monitoring.performance.timing-enabled=true
library.monitoring.performance.slow-request-threshold-ms=1000

# Actuator endpoints
management.endpoints.web.exposure.include=health,info,metrics,prometheus
```

### **Customization Options:**

```yaml
library:
  monitoring:
    metrics:
      enabled: true                    # Enable/disable metrics
      prefix: "library"               # Metrics prefix
      enable-search-metrics: true     # Book search tracking
      enable-security-metrics: true   # Auth error tracking
      enable-rental-metrics: true     # Borrow/return tracking
    
    performance:
      timing-enabled: true             # Enable method timing
      slow-request-threshold-ms: 1000  # Slow request alert
      memory-tracking-enabled: true    # JVM memory monitoring
    
    application:
      name: "library-service"         # Service name for tags
      environment: "production"       # Environment tag
```

---

## 🔧 USAGE EXAMPLES

### **1. Recording Custom Metrics:**

```java
@RestController
@RequiredArgsConstructor
public class BookController {
    
    private final LibraryMetrics metrics;
    
    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestBody BorrowRequest request) {
        try {
            // Business logic...
            bookService.borrowBook(request.getBookId(), request.getUserId());
            
            // Record metric
            metrics.recordBookBorrow(
                request.getBookId(), 
                "Book Title", 
                "username", 
                "STANDARD"
            );
            
            return ResponseEntity.ok("Book borrowed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Borrow failed");
        }
    }
}
```

### **2. Performance Timing:**

```java
@Service
public class BookService {
    
    @Autowired
    private LibraryMetrics metrics;
    
    public List<Book> searchBooks(String query) {
        var timer = metrics.startTiming();
        
        try {
            // Expensive search operation
            List<Book> results = performSearch(query);
            
            // Record search metrics
            metrics.recordBookSearch(
                query, 
                results.size(), 
                timer.getDurationMs(), 
                "advanced"
            );
            
            return results;
        } catch (Exception e) {
            // Still record the attempt
            metrics.recordBookSearch(query, 0, timer.getDurationMs(), "failed");
            throw e;
        }
    }
}
```

### **3. Event-Driven Metrics:**

```java
// Events automatically trigger metrics collection
@Service
public class UserService {
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    public User registerUser(String username, String email) {
        User user = createUser(username, email);
        
        // Publish event - metrics recorded automatically
        eventPublisher.publishEvent(
            new UserRegistrationEvent(this, username, email, "READER")
        );
        
        return user;
    }
}
```

---

## 📈 GRAFANA DASHBOARD

### **Pre-configured Panels:**

1. **📊 System Overview**
   - Application status indicator
   - Key counters (books, users, rentals)
   - Memory usage gauge

2. **🔍 HTTP Requests**
   - Request rate per endpoint
   - Response time percentiles
   - Status code distribution
   - Error rate tracking

3. **📚 Business Metrics**
   - User registration trends
   - Book search activity
   - Borrowing patterns
   - Authentication errors

4. **⚙️ Performance Monitoring**
   - JVM memory usage
   - Thread counts
   - GC activity
   - CPU utilization

### **Custom Queries Examples:**

```promql
# Search requests per minute
rate(library_books_searches_total[5m]) * 60

# Average response time
rate(http_server_requests_seconds_sum[5m]) / rate(http_server_requests_seconds_count[5m])

# Active vs Total books ratio
library_rentals_active / library_books_total

# Memory usage percentage
library_system_memory_usage_ratio * 100
```

---

## 🧪 TESTING

### **Unit Tests:**

```bash
# Run specific monitoring tests
mvn test -Dtest=*MetricsTest

# Full test suite
mvn test
```

### **Integration Tests:**

```bash
# With TestContainers
mvn verify -P integration-tests
```

### **Performance Tests:**

```bash
# Load testing
./scripts/load_test.sh

# Memory leak testing
./scripts/memory_test.sh
```

### **Quality Metrics:**

```bash
# Code coverage
mvn jacoco:report

# Quality gate
mvn sonar:sonar
```

---

## 🚀 DEPLOYMENT

### **Development:**
```bash
# H2 database, full monitoring
mvn spring-boot:run -Dspring-boot.run.profiles=demo
```

### **Staging:**
```bash
# PostgreSQL, enhanced monitoring
export SPRING_PROFILES_ACTIVE=staging
java -jar library-service.jar
```

### **Production:**
```bash
# Full production setup with external monitoring
export SPRING_PROFILES_ACTIVE=production
export LIBRARY_MONITORING_ENVIRONMENT=production
java -Xmx1g -jar library-service.jar
```

### **Docker Deployment:**
```bash
# Build image
docker build -t library-service:latest .

# Run with monitoring stack
docker-compose up -d
```

---

## 🔍 MONITORING & ALERTS

### **Health Checks:**
```bash
# Application health
curl http://localhost:8081/actuator/health

# Readiness probe
curl http://localhost:8081/actuator/health/readiness

# Liveness probe
curl http://localhost:8081/actuator/health/liveness
```

### **Metrics Validation:**
```bash
# Check all library metrics
curl -s http://localhost:8081/actuator/prometheus | grep ^library_

# Verify specific counter
curl -s http://localhost:8081/actuator/prometheus | grep library_books_searches_total
```

### **Performance Benchmarks:**
- **API Response Time**: < 100ms (95th percentile)
- **Search Operations**: < 200ms average
- **Memory Usage**: < 512MB normal operation
- **Database Connections**: < 10 concurrent

---

## 🛠️ TROUBLESHOOTING

### **Common Issues:**

#### **Metrics not appearing:**
```bash
# Check monitoring configuration
curl http://localhost:8081/actuator/configprops | grep monitoring

# Verify meter registry
curl http://localhost:8081/actuator/metrics
```

#### **Slow performance:**
```bash
# Check slow requests
curl http://localhost:8081/actuator/prometheus | grep duration

# Memory analysis
curl http://localhost:8081/actuator/metrics/jvm.memory.used
```

#### **Database connection issues:**
```bash
# Check database health
curl http://localhost:8081/actuator/health/db

# Connection pool status
curl http://localhost:8081/actuator/metrics/hikaricp.connections
```

---

## 📚 BEST PRACTICES

### **✅ Do:**
- Use `LibraryMetrics` facade for recording metrics
- Enable async processing for event handling
- Monitor both technical and business metrics
- Set up alerts for critical thresholds
- Regular performance testing

### **❌ Don't:**
- Direct access to `MeterRegistry` in business code
- Synchronous metrics recording in critical paths
- High-cardinality tags (user IDs, timestamps)
- Missing error handling in metrics code
- Hardcoded configuration values

### **🎯 Performance Tips:**
- Metrics recording is async by default
- Use AOP for cross-cutting concerns
- Cache expensive gauge calculations
- Set appropriate scrape intervals
- Monitor memory usage of metrics

---

## 🔮 FUTURE ENHANCEMENTS

### **Planned Features:**
1. **🔄 Distributed Tracing** - OpenTelemetry integration
2. **📧 Smart Alerting** - Email/Slack notifications
3. **🤖 ML Analytics** - Predictive monitoring
4. **📱 Mobile Dashboard** - Real-time mobile access
5. **🔐 Enhanced Security** - Metrics access control

### **Roadmap:**
- **Q1 2024**: Tracing & Alerting
- **Q2 2024**: ML Analytics
- **Q3 2024**: Mobile Dashboard
- **Q4 2024**: Advanced Security

---

## 🎉 CONCLUSION

Library Management System демонстрирует современные практики разработки enterprise-приложений с полноценным мониторингом. Система готова к production deployment и может масштабироваться для больших нагрузок.

### **🏆 Key Achievements:**
- ✅ **Production-Ready** архитектура
- ✅ **Comprehensive Monitoring** с 100+ метриками
- ✅ **Zero-Configuration** setup
- ✅ **High Performance** с async processing
- ✅ **Enterprise Quality** код и тестирование

**Happy Monitoring! 📊✨**