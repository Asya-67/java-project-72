# Этап сборки
FROM openjdk:22-jdk-slim AS builder

WORKDIR /app

# Копируем gradle wrapper и конфиги
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

RUN chmod +x gradlew

# Копируем исходники и конфиг
COPY src ./src
COPY config ./config

# Собираем shadowJar, без тестов
RUN ./gradlew clean shadowJar --no-daemon -x test

# Этап продакшена
FROM openjdk:22-jre-slim

WORKDIR /app

# Копируем готовый jar из этапа сборки
COPY --from=builder /app/build/libs/app-1.0-SNAPSHOT-all.jar ./app.jar

# Порт приложения
ENV PORT=7000
EXPOSE $PORT

# Команда запуска
CMD ["java", "-jar", "app.jar"]
