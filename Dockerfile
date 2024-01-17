#
# Build stage
#
FROM maven:3.8.1-openjdk-17-slim AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /target/metro-backend-0.0.1-SNAPSHOT.jar metro-backend.jar

# JVM Options
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAM=72m -Xss512k -XX:+UseSerialGC"

# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","metro-backend.jar"]
