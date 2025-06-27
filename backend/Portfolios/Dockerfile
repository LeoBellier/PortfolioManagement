FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# Copy the Gradle or Maven build files
COPY . .

# Build the application (use Gradle or Maven accordingly)
RUN ./gradlew clean build -x test

# Use a lightweight JDK runtime for the final image
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
