FROM openjdk:21-jdk-slim

COPY "." "."

RUN ./gradlew :application:bootJar

CMD ["java", "-jar", "application/build/libs/application-0.0.1-SNAPSHOT.jar"]
