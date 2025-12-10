FROM openjdk:22-jdk-slim

ARG JAR_FILE=target/doctor-appointment-system.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
