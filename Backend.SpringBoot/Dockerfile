
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package


FROM eclipse-temurin:21-jre
WORKDIR /app


COPY --from=build /app/target/*.jar app.jar

# Copy wait-for-postgres script
COPY wait-for-postgres.sh .
RUN chmod +x wait-for-postgres.sh

EXPOSE 8080


CMD ["./wait-for-postgres.sh", "java", "-jar", "app.jar"]
