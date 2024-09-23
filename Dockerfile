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

# "java", utilizado para executar a aplicação Java
# "-jar", utilizado para informar que o arquivo a ser executado é um .jar
# "forum.jar" é o nome do arquivo .jar que será executado pela aplicação Java que está sendo executada pelo Docker
ENTRYPOINT ["java", "-jar", "forum.jar"]