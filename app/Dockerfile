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

# Собираем shadowJar вместо обычного build
RUN ./gradlew clean shadowJar --no-daemon

# Пробрасываем порт из переменной окружения или дефолт 7070
ENV PORT 7070
EXPOSE $PORT

# Запуск приложения через shadowJar
CMD ["sh", "-c", "java -jar build/libs/app-1.0-SNAPSHOT.jar --server.port=$PORT"]
