# Forum API - Spring Boot com Kotlin

Uma API RESTful para um fórum de perguntas e respostas, desenvolvida como estudo e portfólio. O projeto utiliza tecnologias modernas como Spring Boot, Kotlin, MySQL, Docker, autenticação JWT, Swagger e migrations com Flyway.

## Tecnologias Utilizadas

- **Spring Boot 3.3.2**
- **Kotlin 1.9.24**
- **MySQL 8.3.0**
- **Docker**
- **JWT (JSON Web Token)**
- **Swagger (Springdoc OpenAPI)**
- **Flyway**

## Requisitos

Para executar o projeto, certifique-se de ter as seguintes ferramentas instaladas:

- **Java 17**
- **Maven**
- **Docker e Docker Compose**
- **MySQL**

## Configuração do Ambiente

### Arquivos de Configuração

O projeto possui os seguintes arquivos de configuração:

- `application.yml`: Define o perfil padrão como `dev`.
- `application-dev.yml`: Configuração para ambiente de desenvolvimento.
- `application-prod.yml`: Configuração para produção.
- `application-hml.yml`: Configuração para homologação.
- `application-test.yml`: Configuração para testes.

#### Exemplo de Configuração (`application-dev.yml`):

```yaml
spring:
  datasource:
    driverClassName: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/forum
    username: root
    password: root

  jpa:
    open-in-view: false
    properties:
      hibernate:
        show_sql: true
        format_sql: true

  flyway:
    repair: true

logging:
  level:
    org.springframework.security: DEBUG
    org.hibernate.SQL: DEBUG
    org.flywaydb: DEBUG

security:
  jwt:
    expiration: 86400000
    secret: "chaveSecreta"
```

## Instruções de Execução

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/rodrigobn/forum_service
   cd forum_service
   ```

2. **Compile o projeto com Maven:**

   ```bash
   mvn clean install
   ```

3. **Configure o Docker:**
   Certifique-se de que o Docker esteja instalado e funcionando. Utilize o `Dockerfile` para criar a imagem e o container:

   ```bash
   docker build -t forum-api .
   docker run -d -p 3306:3306 --name forum-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=forum mysql:8.3.0
   ```

4. **Execute as migrations do Flyway:**
   O Flyway será executado automaticamente ao iniciar o aplicativo, garantindo que o banco de dados esteja atualizado.

5. **Inicie a aplicação:**

   ```bash
   mvn spring-boot:run
   ```

## Documentação e Testes

A API está documentada com Swagger. Após iniciar a aplicação, acesse a URL:

```
http://localhost:8080/swagger-ui.html
```

## Autenticação

A API utiliza autenticação JWT. Você pode importar o arquivo JSON de requisições para o Insomnia, fornecido no repositório, para facilitar os testes.

Credenciais padrão para acesso:

- **Usuário:** admin
- **Senha:** admin
