# Этап сборки Spring Boot приложения
FROM maven:3.9.9-eclipse-temurin-21 AS spring-builder
WORKDIR /app

# Копируем pom.xml и подгружаем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline

# Копируем исходники и собираем jar
COPY src ./src
RUN mvn clean package -DskipTests


# Финальный этап — только JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Копируем собранный jar из предыдущего этапа
COPY --from=spring-builder /app/target/*.jar app.jar

# Указываем порт
EXPOSE 8080

# Запуск приложения
CMD ["java", "-jar", "app.jar"]
