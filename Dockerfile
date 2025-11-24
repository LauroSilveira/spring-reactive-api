# Compile and package project
FROM maven:3-eclipse-temurin-25-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

# Run application
FROM eclipse-temurin:25-alpine
WORKDIR /reactive-api
COPY --from=build target/*.jar  reactive-api.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "reactive-api.jar"]
