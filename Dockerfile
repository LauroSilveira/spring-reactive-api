FROM eclipse-temurin:21-alpine
VOLUME /tmp
COPY target/*.jar  reactive-api.jar
ENTRYPOINT ["java", "-jar", "/reactive-api.jar"]
