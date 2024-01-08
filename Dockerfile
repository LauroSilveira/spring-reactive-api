# Compile and package project
FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

# Run application
FROM eclipse-temurin:21-alpine
WORKDIR /reactive-api
COPY --from=build target/*.jar  reactive-api.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "reactive-api.jar"]
