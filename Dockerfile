# Compile and package project
FROM maven:3.8.6-eclipse-temurin-17-alpine AS build
COPY . .
RUN mvn clean package

# Run application
FROM eclipse-temurin:21-alpine
VOLUME /tmp
COPY --from=build target/*.jar  reactive-api.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "reactive-api.jar"]
