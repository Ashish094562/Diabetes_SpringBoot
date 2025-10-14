# Stage 1: build jar
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app
COPY Backend.SpringBoot/pom.xml .
COPY Backend.SpringBoot/src ./src
RUN mvn -B -DskipTests package

# Stage 2: run jar
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
