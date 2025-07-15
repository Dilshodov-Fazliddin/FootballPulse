FROM openjdk:17-jdk-slim

WORKDIR /opt/app

COPY target/footballpulse-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]