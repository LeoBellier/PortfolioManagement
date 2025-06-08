FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/reporting-service-0.0.1-SNAPSHOT.jar reporting-service.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "reporting-service.jar"]
