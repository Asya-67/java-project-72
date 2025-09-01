# ======================
# Этап сборки
# ======================
FROM eclipse-temurin:22-jdk AS builder

WORKDIR /app

# Копируем Gradle wrapper и конфиги
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

# ======================
# Этап продакшена
# ======================
FROM eclipse-temurin:22-jdk

WORKDIR /app

# Копируем готовый jar из этапа сборки
COPY --from=builder /app/build/libs/app-1.0-SNAPSHOT-all.jar ./app.jar

# Устанавливаем порт приложения
ENV PORT=7000
EXPOSE $PORT

# Профиль Spring по умолчанию — prod
ENV SPRING_PROFILES_ACTIVE=prod

# Команда запуска с использованием профиля
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
