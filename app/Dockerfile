FROM openjdk:22-jdk-slim

WORKDIR /app

# Копируем Gradle wrapper и папку gradle
COPY gradlew .
COPY gradle ./gradle

# Копируем Gradle Kotlin DSL файлы
COPY build.gradle.kts .
COPY settings.gradle.kts .

RUN chmod +x gradlew

# Копируем исходный код и конфигурацию Checkstyle
COPY src ./src
COPY config ./config

# Собираем проект
RUN ./gradlew build --no-daemon

# Пробрасываем порт 7000
EXPOSE 7000

# Запуск приложения с нужным JAR
CMD ["java", "-jar", "build/libs/app-1.0-SNAPSHOT-all.jar"]
