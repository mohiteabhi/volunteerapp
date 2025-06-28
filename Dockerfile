# Stage 1: build with Maven
FROM maven:3.8.7-eclipse-temurin-17 AS builder
WORKDIR /app

# copy pom and download dependencies
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B

# copy sources and build JAR
COPY src src
RUN ./mvnw clean package -DskipTests

# Stage 2: runtime image
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# copy the built JAR
COPY --from=builder /app/target/*.jar app.jar

# expose the port your Spring Boot uses
EXPOSE 8080

# run the app
ENTRYPOINT ["java","-jar","app.jar"]
