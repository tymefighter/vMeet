FROM openjdk:21-jdk-slim

COPY "." "."

CMD ["./gradlew", ":application:bootJar"]

COPY "application/build/libs/application-0.0.1-SNAPSHOT.jar" "application-0.0.1-SNAPSHOT.jar"

CMD ["java", "-jar", "application/build/libs/application-0.0.1-SNAPSHOT.jar"]
