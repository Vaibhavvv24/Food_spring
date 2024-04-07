# Stage 1: Build the Java application
FROM maven:3.8.5-openjdk-17 AS build-stage

WORKDIR /my-app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean install -DskipTests

# Stage 2: Build the docker image.
FROM openjdk:17-jdk-alpine AS production-stage

WORKDIR /my-app

EXPOSE 5000

COPY --from=build-stage /my-app/target/foodOrder-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "./foodOrder-0.0.1-SNAPSHOT.jar"]