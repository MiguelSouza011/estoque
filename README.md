# Estoque API

API REST para gerenciamento de estoque, desenvolvida com Java e Spring Boot. A aplicação centraliza o cadastro de produtos, categorias e fornecedores, registra entradas e saídas de estoque e protege as operações com autenticação JWT e autorização baseada em funções.

## Funcionalidades

- Cadastro, consulta, atualização e exclusão de produtos.
- Pesquisa paginada de produtos por nome ou identificador.
- Consulta de produtos com estoque abaixo do limite configurado.
- Cadastro, consulta, atualização e exclusão de categorias.
- Cadastro, consulta, atualização e exclusão de fornecedores.
- Validação de CNPJ e dos dados recebidos pela API.
- Registro e consulta de movimentações de estoque.
- Movimentações dos tipos `ENTRADA` e `SAIDA`.
- Bloqueio de saídas que deixariam o estoque negativo.
- Cadastro público de usuários com a função `USER`.
- Cadastro de administradores restrito a usuários com a função `ADMIN`.
- Login com geração de token JWT.
- Senhas protegidas com BCrypt.
- Alertas automáticos por e-mail para produtos com estoque baixo.
- Tratamento centralizado de erros e exceções de negócio.

## Tecnologias

- Java 25
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA e Hibernate
- MySQL
- Spring Security
- JWT e BCrypt
- Spring Boot Mail
- Jakarta Validation
- MapStruct e Lombok
- Maven

## Como utilizar

### Pré-requisitos

- JDK 25
- MySQL em execução
- Git
- Maven (opcional, pois o projeto inclui Maven Wrapper)

### Instalação

Clone o repositório:

```bash
git clone https://github.com/MiguelSouza011/estoque.git
cd estoque
```

Crie um banco de dados chamado `estoque` no MySQL e configure as variáveis de ambiente:

```bash
DB_URL=jdbc:mysql://localhost:3306/estoque
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
DB_DRIVER=com.mysql.cj.jdbc.Driver
JWT_SECRET=uma-chave-secreta
MAIL_USERNAME=seu_email@gmail.com
MAIL_PASSWORD=sua_senha_de_aplicativo
STOCK_ALERT_RECIPIENT=destinatario@example.com
```

As credenciais devem ser mantidas fora do código-fonte. Para envio por Gmail, utilize uma senha de aplicativo em vez da senha principal da conta.

### Execução

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

A API é executada, por padrão, em `http://localhost:8080`.

### Autenticação

Cadastre um usuário comum:

```http
POST /auth/register
```

Faça login:

```http
POST /auth/login
```

Após o login, envie o token retornado nas requisições protegidas:

```http
Authorization: Bearer <seu-token>
```

As operações administrativas, como criar, atualizar e excluir produtos, categorias e fornecedores, exigem um usuário com a função `ADMIN`. O endpoint `POST /auth/admin` também é protegido e só pode ser utilizado por um administrador autenticado.

## Principais endpoints

### Autenticação

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `POST` | `/auth/login` | Autentica o usuário e retorna um JWT |
| `POST` | `/auth/register` | Cadastra um usuário com função `USER` |
| `POST` | `/auth/admin` | Cadastra um administrador |

### Produtos

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `GET` | `/products` | Lista produtos com paginação e filtros |
| `GET` | `/products/{id}` | Consulta um produto |
| `GET` | `/products/low-stock` | Lista produtos com estoque baixo |
| `POST` | `/products` | Cadastra um produto |
| `PUT` | `/products/{id}` | Atualiza um produto |
| `DELETE` | `/products/{id}` | Exclui um produto |

### Categorias, fornecedores e movimentações

| Recurso | Endpoints |
| --- | --- |
| Categorias | `GET /categories`, `GET /categories/{id}`, `POST /categories`, `PUT /categories/{id}`, `DELETE /categories/{id}` |
| Fornecedores | `GET /suppliers`, `GET /suppliers/{id}`, `POST /suppliers`, `PUT /suppliers/{id}`, `DELETE /suppliers/{id}` |
| Movimentações | `GET /movements`, `GET /movements/{id}`, `POST /movements` |
| E-mail | `POST /emails` para testar o envio SMTP configurado |

## Alertas de estoque baixo

O serviço verifica os produtos a cada cinco minutos, após um atraso inicial de dez segundos. Quando encontra produtos abaixo do limite configurado, envia um e-mail para `STOCK_ALERT_RECIPIENT`.

O limite padrão utilizado pela aplicação é de 10 unidades e pode ser ajustado pela propriedade `stock.low-threshold`.

## Arquitetura

O projeto utiliza arquitetura em camadas:

```text
Controller → Service → Repository → MySQL
```

```text
src/main/java/com/miguelsouza/estoque
├── controller
├── service
├── repository
├── entities
├── dto
├── mapper
├── security
└── exceptions
```

Controllers recebem as requisições, services concentram as regras de negócio, repositories acessam o banco de dados e os DTOs/mappers organizam os dados expostos pela API.

## Testes

Execute os testes automatizados com:

```bash
./mvnw test
```

No Windows:

```powershell
.\mvnw.cmd test
```

Também é possível testar os endpoints com Postman, Insomnia, Bruno ou qualquer cliente HTTP.

## Ajuda e contribuição

- Consulte os controllers em `src/main/java/com/miguelsouza/estoque/controller` para verificar os contratos atuais da API.
- Confira `src/main/resources/application.yml` para as propriedades de execução.
- Abra uma [issue](https://github.com/MiguelSouza011/estoque/issues) para relatar problemas ou sugerir melhorias.
- Consulte o [código-fonte no GitHub](https://github.com/MiguelSouza011/estoque).

## Autor

**Miguel Souza**

Projeto desenvolvido para praticar desenvolvimento backend com Java, Spring Boot, APIs REST, persistência de dados, autenticação, autorização e integração com serviços de e-mail.

- GitHub: [@MiguelSouza011](https://github.com/MiguelSouza011)

## Status

Em desenvolvimento.
