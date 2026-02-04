# ---- BUILD STAGE ----
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copia apenas o backend
COPY backend ./backend
WORKDIR /app/backend

# Dá permissão para executar o Maven Wrapper
RUN chmod +x mvnw

# Compila sem testes
RUN ./mvnw clean package -DskipTests


# ---- RUNTIME STAGE ----
FROM eclipse-temurin:21-jre AS run
WORKDIR /app

# Copia o JAR gerado
COPY --from=build /app/backend/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
