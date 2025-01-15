#Dockerfile multi-stage
# Build

FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml /app/
COPY src /app/src
RUN mvn clean package -DskipTests

# Run

FROM eclipse-temurin:17
WORKDIR /app
COPY --from=build /app/target/*.jar .
EXPOSE 8080
CMD ["java", "-jar", "todolist-0.0.1-SNAPSHOT.jar"]
