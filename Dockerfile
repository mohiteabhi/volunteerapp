# Stage 1: build with Maven
FROM maven:3.8.7-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy just wrapper and make it executable
COPY mvnw ./
COPY .mvn .mvn
RUN chmod +x mvnw

# Pre‑fetch dependencies (offline mode)
RUN ./mvnw dependency:go-offline -B

# Copy the rest of your source and build
COPY pom.xml ./
COPY src src/
RUN ./mvnw clean package -DskipTests -B

# Stage 2: runtime image
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Pull in the JAR you just built
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
