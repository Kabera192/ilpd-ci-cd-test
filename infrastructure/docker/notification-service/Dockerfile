# Build stage
FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /build

# Copy Maven wrapper if it exists
COPY notification-service/mvnw* .
COPY notification-service/.mvn .mvn

# Copy parent pom.xml first
COPY pom.xml ./pom.xml

# Install parent pom in local repo
RUN mvn install -N

# Create directories for all microservices
RUN mkdir -p auth-service reporting-service payment-service \
    registration-service hostel-service service-registry \
    inventory-service notification-service gateway-service \
    academic-service

COPY auth-service/pom.xml ./auth-service/pom.xml
COPY reporting-service/pom.xml ./reporting-service/pom.xml
COPY payment-service/pom.xml ./payment-service/pom.xml
COPY registration-service/pom.xml ./registration-service/pom.xml
COPY hostel-service/pom.xml ./hostel-service/pom.xml
COPY service-registry/pom.xml ./service-registry/pom.xml
COPY inventory-service/pom.xml ./inventory-service/pom.xml
COPY academic-service/pom.xml ./academic-service/pom.xml
COPY gateway-service/pom.xml ./gateway-service/pom.xml

# Copy and build shared-library first
COPY shared-library ./shared-library
RUN mvn clean install -DskipTests -f shared-library/pom.xml

# Copy service pom.xml and download dependencies
COPY notification-service/pom.xml ./notification-service/pom.xml
RUN mvn dependency:go-offline -f notification-service/pom.xml

# Copy source code and build
COPY notification-service/src ./notification-service/src
RUN mvn -B --no-transfer-progress clean package -DskipTests -f notification-service/pom.xml

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

LABEL maintainer="support@ilpd.ac.rw"
LABEL service="notification-service"

# Create non-root user
RUN addgroup -S app && adduser -S app -G app

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /build/notification-service/target/*.jar app.jar

# Change ownership
RUN chown -R app:app /app

# Switch to non-root user
USER app

EXPOSE 8091

ENTRYPOINT ["java", "-jar", "app.jar"]
