# Этап сборки
FROM eclipse-temurin:22-jdk AS builder

WORKDIR /app

# Копируем gradle wrapper и конфиги
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

RUN chmod +x gradlew

# Копируем исходники, шаблоны и ресурсы
COPY src ./src
COPY config ./config

# Собираем shadowJar без тестов
RUN ./gradlew clean shadowJar --no-daemon

# Этап продакшена
FROM eclipse-temurin:22-jdk

WORKDIR /app

# Копируем готовый jar из этапа сборки
COPY --from=builder /app/build/libs/app-1.0-SNAPSHOT-all.jar ./app.jar

# Порт приложения
ENV PORT=7000
EXPOSE $PORT

# Устанавливаем рабочую директории
# COPY src/main/resources ./resources

# Команда запуска
CMD ["java", "-jar", "app.jar"]
