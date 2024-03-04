# Build stage
FROM maven:3.9.6-amazoncorretto-21 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

# Package stage
FROM openjdk:21-jdk-slim
COPY --from=build /target/metro-backend-0.0.1-SNAPSHOT.jar metro-backend.jar

# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","metro-backend.jar"]