# Используем официальный образ OpenJDK 22 с Gradle
FROM openjdk:22-jdk-slim

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем Gradle wrapper и build файлы из Build Context
COPY ./gradlew ./gradlew
COPY ./gradle ./gradle
COPY ./build.gradle ./build.gradle
COPY ./settings.gradle ./settings.gradle

# Делаем gradlew исполняемым
RUN chmod +x ./gradlew

# Скачиваем зависимости и билдим проект (без тестов для ускорения)
RUN ./gradlew build -x test --no-daemon

# Копируем весь код проекта из Build Context
COPY ./ ./

# Собираем fat-jar с помощью Shadow плагина
RUN ./gradlew shadowJar --no-daemon

# Указываем переменную окружения для порта приложения
ENV PORT=7000

# Команда запуска приложения
CMD ["java", "-jar", "build/libs/app-1.0-SNAPSHOT-all.jar"]
