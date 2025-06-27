FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/DiscoveryServer-0.0.1-SNAPSHOT.jar DiscoveryServer.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "DiscoveryServer.jar"]
