# --- Build stage: compile and package the Spring Boot jar ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
# Download dependencies first so they are cached between builds
COPY pom.xml .
RUN mvn -q -B dependency:go-offline
COPY src ./src
RUN mvn -q -B package -DskipTests

# --- Run stage: only the Java runtime and the jar ---
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Production profile by default (Postgres, no demo data).
# Run with -e SPRING_PROFILES_ACTIVE=default to use the in-memory H2 database.
ENV SPRING_PROFILES_ACTIVE=prod
# Free hosting plans have ~512 MB of memory; keep the JVM inside it
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75"

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
