# 🌍 Projeto CRUD com Spring Boot 3 + Java 21 + DynamoDB (LocalStack)

Um projeto moderno e enxuto com foco em produtividade e testes locais com AWS DynamoDB, utilizando:
- ✅ Spring Boot 3
- ✅ Java 21
- ✅ AWS SDK v2 (Enhanced Client)
- ✅ LocalStack para simular AWS localmente
- ✅ Arquitetura MVC

---

## ⚙️ Tecnologias

| Tecnologia     | Versão/Recurso         |
|----------------|------------------------|
| Java           | 21                     |
| Spring Boot    | 3.x                    |
| DynamoDB       | via LocalStack         |
| AWS SDK v2     | `software.amazon.awssdk:dynamodb` |
| Docker + Compose | Para ambiente local |

---

## 🚀 Como rodar o projeto localmente

### 1️⃣ Subir o LocalStack com DynamoDB

Crie o arquivo `docker-compose.yml`:

```yaml
version: '3.8'
services:
  localstack:
    image: localstack/localstack:1.5.0
    ports:
      - "4566:4566"
    environment:
      - SERVICES=dynamodb
      - DEBUG=1
    volumes:
      - "./.localstack:/var/lib/localstack"
      - "/var/run/docker.sock:/var/run/docker.sock"
```

Suba o container:

```bash
docker-compose up -d
```

---

### 2️⃣ Criar a tabela DynamoDB no LocalStack

Use `awslocal` (recomendado):

```bash
awslocal dynamodb create-table \
    --table-name global01 \
    --key-schema AttributeName=id,KeyType=HASH \
    --attribute-definitions AttributeName=id,AttributeType=S \
    --billing-mode PAY_PER_REQUEST \
    --region ap-south-1
```

✅ Você pode verificar se a tabela foi criada com:

```bash
awslocal dynamodb list-tables --region ap-south-1
```

---

### 3️⃣ Configurar o ambiente local

#### 📁 `src/main/resources/application-local.properties`

```properties
aws.region=ap-south-1
aws.dynamodb.endpoint=http://localhost:4566
aws.dynamodb.tableName=global01

server.port=8080
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
spring.devtools.restart.enabled=false
```

No IntelliJ, ative o profile `local`:

**Run > Edit Configurations > VM options:**

```bash
-Dspring.profiles.active=local
```

Ou via terminal:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

---

## 🧪 Testes com cURL

### ➕ Criar customerEntity

```bash
curl -X POST http://localhost:8080/pessoas \
  -H "Content-Type: application/json" \
  -d '{"id":"1","nome":"Ana Clara","idade":27}'
```

### 🔍 Buscar por ID

```bash
curl http://localhost:8080/pessoas/1
```

### 📄 Listar todas as pessoas

```bash
curl http://localhost:8080/pessoas
```

### ❌ Deletar por ID

```bash
curl -X DELETE http://localhost:8080/pessoas/1
```

---

## 🧱 Estrutura do Projeto

```
src/
 └── main/
     ├── java/com/seuprojeto/
     │   ├── controller/
     │   ├── service/
     │   ├── repository/
     │   ├── model/entity/
     │   └── config/
     └── resources/
         ├── application.properties
         └── application-local.properties
```

---

## 🛠 Requisitos

- Java 21+
- Maven 3.8+
- Docker e Docker Compose
- AWS CLI + `awslocal` (opcional, mas recomendado)

---

## 💡 Dicas

- Prefira `@Value` ou `@ConfigurationProperties` para configurar o nome da tabela dinamicamente.
- Não esqueça de usar `@DynamoDbPartitionKey` na propriedade `id`.
- Evite DevTools com DynamoDB (classloader issues).

---

## 📫 Contato

Contribuições, dúvidas ou melhorias? Fique à vontade para abrir uma issue ou enviar PR. 😉

---

## 🧡 Feito com dedicação para quem ama backend limpo, performático e preparado para cloud.

```
awslocal sqs send-message \
  --queue-url http://localhost:4566/000000000000/minha-fila \
  --message-body '{"id":"123","nome":"Maria SQS","idade":30}' \
  --region ap-south-1
```