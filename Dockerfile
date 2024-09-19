# Stage 1: Build
FROM openjdk:17 AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Stage 2: Run
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/forum-1.0.0.jar forum.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "forum.jar"]