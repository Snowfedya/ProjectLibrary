package org.example.library.services;

import io.micrometer.core.instrument.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.library.repositories.*;
import org.example.library.models.RentalRequestStatus;
import org.example.library.models.BookStatus;
import org.springframework.stereotype.Service;

/**
 * Сервис для сбора и обновления бизнес-метрик библиотеки
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MetricsService {

    private final MeterRegistry meterRegistry;
    private final BookRepository bookRepository;
    private final LibraryUserRepository userRepository;
    private final RentalRequestRepository rentalRequestRepository;

    // Инжектируемые счетчики
    private final Counter userRegistrationCounter;
    private final Counter bookBorrowCounter;
    private final Counter bookReturnCounter;
    private final Counter bookSearchCounter;
    private final Counter authenticationErrorCounter;
    private final Timer bookSearchTimer;
    private final Timer bookBorrowTimer;

    /**
     * Инициализация Gauge метрик при старте приложения
     */
    public void initializeGauges() {
        // Общее количество книг
        Gauge.builder("library.books.total", this, MetricsService::getTotalBooksCount)
                .description("Total number of books in library")
                .register(meterRegistry);

        // Общее количество пользователей
        Gauge.builder("library.users.total", this, MetricsService::getTotalUsersCount)
                .description("Total number of registered users")
                .register(meterRegistry);

        // Активные займы
        Gauge.builder("library.rentals.active", this, MetricsService::getActiveRentalsCount)
                .description("Number of active book rentals")
                .register(meterRegistry);

        // Просроченные займы
        Gauge.builder("library.rentals.overdue", this, MetricsService::getOverdueRentalsCount)
                .description("Number of overdue book rentals")
                .register(meterRegistry);

        // Доступные книги
        Gauge.builder("library.books.available", this, MetricsService::getAvailableBooksCount)
                .description("Number of available books")
                .register(meterRegistry);

        log.info("Metrics gauges initialized successfully");
    }

    // Методы для получения актуальных значений метрик

    /**
     * Получить общее количество книг
     */
    public double getTotalBooksCount() {
        try {
            return bookRepository.count();
        } catch (Exception e) {
            log.error("Error getting total books count", e);
            return 0;
        }
    }

    /**
     * Получить общее количество пользователей
     */
    public double getTotalUsersCount() {
        try {
            return userRepository.count();
        } catch (Exception e) {
            log.error("Error getting total users count", e);
            return 0;
        }
    }

    /**
     * Получить количество активных займов
     */
    public double getActiveRentalsCount() {
        try {
            return rentalRequestRepository.findByStatus(RentalRequestStatus.APPROVED).size();
        } catch (Exception e) {
            log.error("Error getting active rentals count", e);
            return 0;
        }
    }

    /**
     * Получить количество просроченных займов
     */
    public double getOverdueRentalsCount() {
        try {
            // TODO: Реализовать логику подсчета просроченных займов
            // Пока возвращаем 0, будет реализовано при добавлении полей дат в RentalRequest
            return 0;
        } catch (Exception e) {
            log.error("Error getting overdue rentals count", e);
            return 0;
        }
    }

    /**
     * Получить количество доступных книг
     */
    public double getAvailableBooksCount() {
        try {
            return bookRepository.findRentedBooks(BookStatus.AVAILABLE).size();
        } catch (Exception e) {
            log.error("Error getting available books count", e);
            return 0;
        }
    }

    // Методы для записи событий

    /**
     * Записать событие регистрации пользователя
     */
    public void recordUserRegistration(String userRole) {
        userRegistrationCounter.increment();
        log.debug("User registration recorded: role={}", userRole);
    }

    /**
     * Записать событие займа книги
     */
    public void recordBookBorrow(String bookCategory, String userRole) {
        bookBorrowCounter.increment();
        log.debug("Book borrow recorded: category={}, userRole={}", bookCategory, userRole);
    }

    /**
     * Записать событие возврата книги
     */
    public void recordBookReturn(String bookCategory, boolean onTime) {
        bookReturnCounter.increment();
        log.debug("Book return recorded: category={}, onTime={}", bookCategory, onTime);
    }

    /**
     * Записать событие поиска книги
     */
    public void recordBookSearch(String searchType, int resultsCount) {
        bookSearchCounter.increment();
        log.debug("Book search recorded: type={}, results={}", searchType, resultsCount);
    }

    /**
     * Записать ошибку аутентификации
     */
    public void recordAuthenticationError(String errorType, String endpoint) {
        authenticationErrorCounter.increment();
        log.debug("Authentication error recorded: type={}, endpoint={}", errorType, endpoint);
    }

    /**
     * Засечь время выполнения поиска книг
     */
    public Timer.Sample startBookSearchTimer() {
        return Timer.start(meterRegistry);
    }

    /**
     * Завершить измерение времени поиска
     */
    public void stopBookSearchTimer(Timer.Sample sample, String searchType, int resultsCount) {
        sample.stop(bookSearchTimer);
    }

    /**
     * Засечь время выполнения займа книги
     */
    public Timer.Sample startBookBorrowTimer() {
        return Timer.start(meterRegistry);
    }

    /**
     * Завершить измерение времени займа
     */
    public void stopBookBorrowTimer(Timer.Sample sample, boolean successful) {
        sample.stop(bookBorrowTimer);
    }
}