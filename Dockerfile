# Build stage
FROM maven:3.9.6-openjdk-21-slim AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENV JAVA_TOOL_OPTIONS "-Xms256m -Xmx512m"

ENTRYPOINT ["java", "$JAVA_TOOL_OPTIONS", "-jar", "app.jar"]