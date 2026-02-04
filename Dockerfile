# ---- BUILD STAGE ----
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia apenas o backend
COPY backend ./backend
WORKDIR /app/backend

# Compila sem testes
RUN mvn clean package -DskipTests


# ---- RUNTIME STAGE ----
FROM eclipse-temurin:21-jre AS run
WORKDIR /app

# Copia o JAR gerado
COPY --from=build /app/backend/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
