# =============================
# Stage 1: Build the Spring Boot JAR
# =============================
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and src from your backend folder
COPY Backend.SpringBoot/pom.xml .
COPY Backend.SpringBoot/src ./src

# Build the JAR (skipping tests for faster build)
RUN mvn -B -DskipTests package

# =============================
# Stage 2: Run the JAR
# =============================
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy built jar from previous stage
COPY --from=build /app/target/*.jar app.jar

# Render sets PORT automatically; use it
ENV PORT=8080
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "app.jar"]
