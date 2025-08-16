# Используем официальный образ OpenJDK 22 с Gradle
FROM openjdk:22-jdk-slim

# Рабочая директория внутри контейнера
WORKDIR /app

# Копируем Gradle wrapper и build файлы
COPY ./gradlew ./gradlew
COPY ./gradle ./gradle
COPY ./build.gradle ./build.gradle
COPY ./settings.gradle ./settings.gradle

# Делаем gradlew исполняемым
RUN chmod +x ./gradlew

# Скачиваем зависимости и билдим проект (без тестов)
RUN ./gradlew build -x test --no-daemon

# Копируем весь код проекта
COPY ./ ./

# Собираем fat-jar через Shadow плагин
RUN ./gradlew shadowJar --no-daemon

# Переменная окружения для порта
ENV PORT=7000

# Запуск приложения
CMD ["java", "-jar", "build/libs/app-1.0-SNAPSHOT-all.jar"]
