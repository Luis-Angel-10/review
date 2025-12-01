# ---- COMPILAR EL PROYECTO ----
FROM maven:3.9.6-eclipse-temurin-23 AS builder
WORKDIR /app

# Copiar archivos de proyecto
COPY pom.xml .
COPY src ./src

# Empaquetar (genera review.jar en /app/target)
RUN mvn clean package -DskipTests

# ---- RUNNER (IMAGEN FINAL) ----
FROM eclipse-temurin:23-jre

WORKDIR /app

# Copiamos el JAR al contenedor final
COPY --from=builder /app/target/*.jar app.jar

# Puerto que usará tu app (Render asigna automáticamente, pero debe exponerse)
EXPOSE 8088

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]
