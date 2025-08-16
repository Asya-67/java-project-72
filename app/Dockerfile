FROM openjdk:22-jdk-slim

WORKDIR /app

# Копируем Gradle wrapper и папку gradle
COPY gradlew .
COPY gradle ./gradle

# Копируем Gradle файлы
COPY build.gradle .
COPY settings.gradle .

RUN chmod +x gradlew

# Копируем исходный код
COPY src ./src

# Собираем проект
RUN ./gradlew build --no-daemon

EXPOSE 8080

CMD ["java", "-jar", "build/libs/app.jar"]
