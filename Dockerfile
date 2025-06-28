# Stage 1: build with Maven
FROM maven:3.8.7-eclipse-temurin-17 AS builder
WORKDIR /app

# 1) Copy the pom (so mvnw has a project to work on)
COPY pom.xml ./

# 2) Copy the Maven wrapper and settings, then make it executable
COPY mvnw ./
COPY .mvn .mvn
RUN chmod +x mvnw

# 3) Pre‑fetch all dependencies (offline mode)
RUN ./mvnw dependency:go-offline -B

# 4) Copy the rest of your source and build the JAR
COPY src src/
RUN ./mvnw clean package -DskipTests -B

# Stage 2: runtime image
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port Spring Boot will use
EXPOSE 8080

# Run the app
ENTRYPOINT ["java","-jar","app.jar"]
