#!/bin/bash

# Скрипт для запуска улучшенной системы библиотеки

set -e

echo "=== Запуск улучшенной системы управления библиотекой ==="
echo ""

# Проверяем зависимости
if ! command -v docker &> /dev/null; then
    echo "❌ Docker не установлен. Установите Docker для продолжения."
    exit 1
fi

if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose не установлен."
    exit 1
fi

if ! command -v java &> /dev/null; then
    echo "❌ Java не установлена. Установите Java 21+ для продолжения."
    exit 1
fi

if ! command -v mvn &> /dev/null; then
    echo "❌ Maven не установлен. Установите Maven для продолжения."
    exit 1
fi

echo "✅ Все зависимости установлены"
echo ""

echo "1. Запускаем инфраструктуру..."
docker-compose up -d

echo ""
echo "2. Ждем запуска PostgreSQL..."
sleep 15

echo ""
echo "3. Собираем приложение..."
mvn clean package -DskipTests

echo ""
echo "4. Запускаем приложение..."
nohup java -jar library-service/target/library-service-1.0.0-SNAPSHOT.jar > library-app.log 2>&1 &
APP_PID=$!
echo "PID приложения: $APP_PID"
echo $APP_PID > library-app.pid

echo ""
echo "5. Ждем запуска приложения..."
sleep 30

echo ""
echo "🎉 Система запущена!"
echo ""
echo "=== ДОСТУПНЫЕ СЕРВИСЫ ==="
echo ""
echo "📚 Основное приложение: http://localhost:8080"
echo "📊 Grafana (мониторинг): http://localhost:3000 (admin/admin123)"
echo "🔍 Prometheus (метрики): http://localhost:9090"
echo "📧 MailDev (email тест): http://localhost:1080"
echo ""
echo "=== ТЕСТОВЫЕ АККАУНТЫ ==="
echo ""
echo "Администратор: admin@library.com / admin123"
echo "Библиотекарь: librarian@library.com / librarian123" 
echo "Студент: student@university.edu / student123"
echo ""
echo "=== НОВЫЕ ФУНКЦИИ ==="
echo ""
echo "✨ Восстановление пароля: /forgot-password"
echo "📊 Метрики Prometheus: /actuator/prometheus"
echo "🏥 Health Check: /actuator/health"
echo "📈 Grafana Dashboard с метриками JVM и HTTP"
echo "📧 Email уведомления через MailDev"
echo "🧪 Автоматическая генерация тестовых данных"
echo ""
echo "=== ДЕМОНСТРАЦИЯ ==="
echo ""
echo "1. Откройте http://localhost:8080 и войдите как администратор"
echo "2. Протестируйте восстановление пароля на /forgot-password"
echo "3. Проверьте email в MailDev: http://localhost:1080"
echo "4. Посмотрите метрики в Grafana: http://localhost:3000"
echo "5. Изучите Prometheus метрики: http://localhost:9090"
echo ""
echo "=== ОСТАНОВКА ==="
echo ""
echo "Для остановки выполните: ./stop-enhanced.sh"
echo ""
echo "📄 Логи приложения: tail -f library-app.log"
echo ""

# Показываем последние логи
if [ -f library-app.log ]; then
    echo "Последние логи приложения:"
    tail -20 library-app.log
fi