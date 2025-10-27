# Stage 1: Builder
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Expose ports of services
EXPOSE 8082 8761 8092

# Copy the built JARs only
COPY --from=builder /app/shared-library/target/*.jar shared-lib.jar
COPY --from=builder /app/academic-service/target/*.jar academic.jar
COPY --from=builder /app/auth-service/target/*.jar auth.jar
COPY --from=builder /app/gateway-service/target/*.jar gateway.jar
COPY --from=builder /app/inventory-service/target/*.jar inventory.jar
COPY --from=builder /app/hostel-service/target/*.jar hostel.jar
COPY --from=builder /app/notification-service/target/*.jar notification.jar
COPY --from=builder /app/registration-service/target/*.jar registration.jar
COPY --from=builder /app/reporting-service/target/*.jar reporting.jar
COPY --from=builder /app/payment-service/target/*.jar payment.jar
COPY --from=builder /app/service-registry/target/*.jar registry.jar
ENV PROFILE_MODE=prod
# Run all  apps in parallel
CMD ["sh", "-c", "java -jar gateway.jar & java -jar registry.jar & java -jar payment.jar & java -jar reporting.jar & java -jar registration.jar & java -jar notification.jar & java -jar inventory.jar  & java -jar academic.jar & java -jar auth.jar & java -jar hostel.jar && wait"]
