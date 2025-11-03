# Build stage
FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /build

# Copy Maven wrapper if it exists
COPY service-registry/mvnw* .
COPY service-registry/.mvn .mvn

# Copy parent pom.xml first
COPY pom.xml ./pom.xml

# Create directories for all microservices
RUN mkdir -p auth-service reporting-service payment-service \
    registration-service hostel-service service-registry \
    inventory-service notification-service gateway-service \
    shared-library academic-service

# Copy and build shared-library first
COPY shared-library/pom.xml ./shared-library/pom.xml
COPY shared-library/src ./shared-library/src
RUN cd shared-library && mvn clean install -DskipTests -pl shared-library -am

COPY auth-service/pom.xml ./auth-service/pom.xml
COPY reporting-service/pom.xml ./reporting-service/pom.xml
COPY payment-service/pom.xml ./payment-service/pom.xml
COPY registration-service/pom.xml ./registration-service/pom.xml
COPY hostel-service/pom.xml ./hostel-service/pom.xml
COPY academic-service/pom.xml ./academic-service/pom.xml
COPY inventory-service/pom.xml ./inventory-service/pom.xml
COPY notification-service/pom.xml ./notification-service/pom.xml
COPY gateway-service/pom.xml ./gateway-service/pom.xml

# Copy service pom.xml and download dependencies
COPY service-registry/pom.xml ./service-registry/pom.xml
RUN mvn dependency:resolve -pl service-registry -am || true

# Copy source code and build
COPY service-registry/src ./service-registry/src
RUN mvn -B --no-transfer-progress clean package -DskipTests -pl service-registry -am

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

LABEL maintainer="support@ilpd.ac.rw"
LABEL service="service-registry"

# Create non-root user
RUN addgroup -S app && adduser -S app -G app

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /build/service-registry/target/*.jar app.jar

# Change ownership
RUN chown -R app:app /app

# Switch to non-root user
USER app

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]
