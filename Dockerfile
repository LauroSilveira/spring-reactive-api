FROM eclipse-temurin:17-alpine
VOLUME /tmp
COPY target/reactive-api-0.0.1-SNAPSHOT.jar reactive-api.jar
ENTRYPOINT ["java", "-jar", "/reactive-api.jar"]
