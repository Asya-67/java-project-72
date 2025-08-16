FROM openjdk:22-jdk-slim

WORKDIR /app

# Копируем Gradle wrapper и папку gradle
COPY app/gradlew .
COPY app/gradle ./gradle

# Копируем Gradle файлы из корня
COPY build.gradle .
COPY settings.gradle .

RUN chmod +x gradlew

COPY app/src ./src

RUN ./gradlew build --no-daemon

EXPOSE 8080

CMD ["java", "-jar", "build/libs/app.jar"]
