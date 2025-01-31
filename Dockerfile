# Stage 1: Build
#utiliza a imagem maven:3.8.6-openjdk-17 como base para a construcao da imagem
FROM maven:3.8.6-openjdk-17 AS build
#cria o diretorio /app
WORKDIR /app
#copia o arquivo pom.xml para o diretorio /app
COPY . .
#executa o comando mvn clean package -DskipTests
RUN mvn clean package -DskipTests

# Stage 2: Run
#utiliza a imagem openjdk:17 como base para a construcao da imagem
FROM openjdk:17
#cria o diretorio /app
WORKDIR /app
#copia o arquivo app.jar para o diretorio /app
COPY --from=build /app/target/*.jar app.jar
#expoe a porta 8080
EXPOSE 8080
#executa o comando java -jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]