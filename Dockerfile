# ---------- COMPILAR LA APP ----------
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn -e -X -U dependency:resolve dependency:resolve-plugins -DskipTests

COPY src ./src
RUN mvn -e -X -U -DskipTests clean package

# ---------- EJECUTAR LA APP ----------
FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8088
ENTRYPOINT ["java", "-jar", "app.jar"]
