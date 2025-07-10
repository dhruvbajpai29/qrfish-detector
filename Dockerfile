# Use lightweight JDK 21 image
FROM eclipse-temurin:21-jdk-alpine

# Create app directory
WORKDIR /app

# Copy everything to the image
COPY . .

# Build the Spring Boot app
RUN ./gradlew build -x test

# Expose the port that Render will inject
EXPOSE 8080

# Set environment variable for Spring Boot to pick up Render's port
ENV PORT=$PORT

# Run the app using the generated jar
CMD ["java", "-jar", "build/libs/qrfish-detector-0.0.1-SNAPSHOT.jar"]
