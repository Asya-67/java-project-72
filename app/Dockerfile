FROM openjdk:22-jdk-slim

WORKDIR /app

# Копируем Gradle wrapper и build файлы из текущей папки (app)
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle build.gradle
COPY settings.gradle settings.gradle

RUN chmod +x gradlew

# Скачиваем зависимости и билдим проект
RUN ./gradlew build -x test --no-daemon

# Копируем весь код проекта
COPY . .

# Собираем fat-jar с Shadow плагином
RUN ./gradlew shadowJar --no-daemon

ENV PORT=7000

CMD java -jar build/libs/app-1.0-SNAPSHOT-all.jar
