# Build stage
FROM maven:3.9-eclipse-temurin-21-alpine AS build

ARG SERVICE_NAME

WORKDIR /build

# Copy parent pom first
COPY pom.xml ./

# Copy all service pom.xml files (needed for Maven reactor)
COPY auth-service/pom.xml ./auth-service/
COPY academic-service/pom.xml ./academic-service/
COPY registration-service/pom.xml ./registration-service/
COPY hostel-service/pom.xml ./hostel-service/
COPY reporting-service/pom.xml ./reporting-service/
COPY notification-service/pom.xml ./notification-service/
COPY payment-service/pom.xml ./payment-service/
COPY gateway-service/pom.xml ./gateway-service/
COPY service-registry/pom.xml ./service-registry/
COPY inventory-service/pom.xml ./inventory-service/

# Copy and install shared library
COPY shared-library ./shared-library
RUN mvn clean install -DskipTests -pl shared-library -am

# Copy service source and build
COPY ${SERVICE_NAME} ./${SERVICE_NAME}
RUN mvn -B --no-transfer-progress clean package -DskipTests -pl ${SERVICE_NAME} -am

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

ARG SERVICE_NAME

WORKDIR /app

COPY --from=build /build/${SERVICE_NAME}/target/*.jar app.jar

RUN addgroup -S app && adduser -S app -G app && chown -R app:app /app
USER app

EXPOSE 8092

ENTRYPOINT ["java", "-jar", "app.jar"]
