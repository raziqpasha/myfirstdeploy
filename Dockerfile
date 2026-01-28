# Use Maven image with Java 11
FROM maven:3.8.6-openjdk-11-slim

# Set working directory
WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src
COPY testng.xml .

# Download dependencies (this layer will be cached)
RUN mvn dependency:go-offline

# Default command: run tests
CMD ["mvn", "clean", "test"]