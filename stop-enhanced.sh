#!/bin/bash

# Скрипт для остановки улучшенной системы библиотеки

echo "=== Остановка улучшенной системы библиотеки ==="
echo ""

# Останавливаем Java приложение
if [ -f library-app.pid ]; then
    APP_PID=$(cat library-app.pid)
    echo "Останавливаем приложение (PID: $APP_PID)..."
    
    if ps -p $APP_PID > /dev/null; then
        kill $APP_PID
        
        # Ждем остановки
        for i in {1..10}; do
            if ! ps -p $APP_PID > /dev/null; then
                echo "✅ Приложение остановлено"
                break
            fi
            echo "⏳ Ждем остановки..."
            sleep 2
        done
        
        # Принудительная остановка если нужно
        if ps -p $APP_PID > /dev/null; then
            echo "⚠️ Принудительная остановка..."
            kill -9 $APP_PID
        fi
    else
        echo "❌ Процесс не найден"
    fi
    
    rm -f library-app.pid
else
    echo "❌ PID файл не найден"
fi

echo ""
echo "Останавливаем Docker сервисы..."
docker-compose down

echo ""
echo "Очистка временных файлов..."
rm -f library-app.log
rm -f nohup.out

echo ""
echo "🏁 Система полностью остановлена"
echo ""
echo "Для полной очистки данных выполните:"
echo "   docker-compose down -v"