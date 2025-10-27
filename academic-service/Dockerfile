# Specify jdk base image
FROM openjdk:21-jdk-alpine AS build

# Set working directory
WORKDIR /app

COPY pom.xml

# Download maven dependencies
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the applicaiton
# Skip tests since they will be run in the CI/CD pipeline
RUN mvn clean package -DskipTests

RUN mkdir -p target/dependency && cd target/dependency && jar -xf ../*.jar

FROM openjdk:21-jdk-alpine

LABEL maintainer="support@ilpd.ac.rw"
LABEL service="academic-service"

# Create non-root user for security
RUN addgroup -S app && adduser -S app -G app

# Set working directory
WORKDIR /app

# Copy the JAR file from build stage
COPY --from=build /app/target/*.jar app.jar

# Change ownership to non-root user
RUN chown -R app:app /app

# Switch to non-root user
USER app:app

EXPOSE 8092

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
