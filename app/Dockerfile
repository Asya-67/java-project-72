# Базовый образ
FROM openjdk:22-jdk-slim

# Рабочая директория внутри контейнера
WORKDIR /app

# Копируем Gradle wrapper и папку gradle
COPY gradlew .
COPY gradle ./gradle

# Копируем основные Gradle файлы
COPY build.gradle .
COPY settings.gradle .

# Делаем gradlew исполняемым
RUN chmod +x gradlew

# Копируем весь исходный код
COPY src ./src

# Собираем проект
RUN ./gradlew build --no-daemon

# Пробрасываем порт (если это веб-приложение)
EXPOSE 8080

# Запуск приложения
CMD ["java", "-jar", "build/libs/app.jar"]
