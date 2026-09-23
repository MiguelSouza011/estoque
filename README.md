# 📦 Estoque API

API REST para gerenciamento de estoque desenvolvida com **Java e Spring Boot**.

O projeto foi desenvolvido com foco em praticar e aplicar conceitos de desenvolvimento backend, incluindo **arquitetura em camadas, JPA/Hibernate, relacionamentos entre entidades, DTOs, MapStruct, validação de dados, tratamento de exceções e regras de negócio**.

## 🎯 Objetivo

O objetivo do projeto é desenvolver uma API para gerenciamento de produtos e seus relacionamentos com categorias e fornecedores, além de controlar as movimentações de entrada e saída do estoque.

A aplicação também busca aplicar boas práticas de desenvolvimento backend e organização de código, utilizando recursos do ecossistema Spring.

---

## 🚀 Funcionalidades

### 📦 Produtos

* Cadastro de produtos
* Consulta de produtos
* Consulta de produto por ID
* Atualização de produtos
* Exclusão de produtos
* Busca de produtos por nome
* Associação do produto a uma categoria
* Associação do produto a um ou mais fornecedores
* Controle da quantidade disponível em estoque

### 🗂️ Categorias

* Cadastro de categorias
* Consulta de categorias
* Consulta por ID
* Atualização
* Exclusão

### 🏢 Fornecedores

* Cadastro de fornecedores
* Consulta de fornecedores
* Consulta por ID
* Atualização
* Exclusão
* Validação de CNPJ
* Associação com produtos

### 🔄 Movimentações de estoque

O projeto possui controle de movimentações de estoque através dos tipos:

* `ENTRADA`
* `SAIDA`

As movimentações possuem:

* Tipo da movimentação
* Quantidade
* Data e hora
* Produto relacionado

Nas entradas, a quantidade é adicionada ao estoque.

Nas saídas, a aplicação verifica se existe quantidade suficiente antes de realizar a operação, evitando que o estoque fique negativo.

---

## 🛠️ Tecnologias utilizadas

* **Java 25**
* **Spring Boot 4.1.1**
* **Spring Web MVC**
* **Spring Data JPA**
* **Hibernate**
* **MySQL**
* **Maven**
* **Lombok**
* **MapStruct**
* **Jakarta Validation**
* **Git**
* **GitHub**

A configuração atual do projeto utiliza Java 25, Spring Boot 4.1.1, Spring Data JPA, MySQL, MapStruct e Lombok.

---

## 🏗️ Arquitetura

O projeto utiliza uma organização baseada em camadas:

```text
src
└── main
    └── java
        └── com.miguelsouza.estoque
            ├── controller
            ├── service
            ├── repository
            ├── entities
            ├── dto
            ├── mapper
            └── exceptions
```

### Controller

Responsável por receber as requisições HTTP e disponibilizar os endpoints da API.

Exemplo:

```text
POST   /products
GET    /products
GET    /products/{id}
PUT    /products/{id}
DELETE /products/{id}
```

A API utiliza `ResponseEntity` para controlar os status HTTP retornados ao cliente.

### Service

Concentra as regras de negócio da aplicação.

Um exemplo importante está nas movimentações de estoque, onde o serviço verifica o produto, atualiza sua quantidade e impede uma saída maior que o estoque disponível.

### Repository

Responsável pela comunicação com o banco de dados utilizando **Spring Data JPA**.

### Entities

Representam as entidades persistidas no banco de dados.

Principais entidades:

```text
Product
Category
Supplier
Movement
```

---

## 🔗 Relacionamentos

O projeto utiliza diferentes tipos de relacionamentos do JPA.

### Produto → Categoria

Um produto pertence a uma categoria:

```text
Category 1 ──────── N Product
```

### Produto ↔ Fornecedor

Um produto pode possuir vários fornecedores e um fornecedor pode estar relacionado a vários produtos:

```text
Product N ──────── N Supplier
```

Esse relacionamento utiliza uma tabela intermediária chamada:

```text
produto_fornecedor
```

### Produto → Movimentações

Um produto pode possuir várias movimentações:

```text
Product 1 ──────── N Movement
```

Esses relacionamentos são implementados utilizando as anotações do JPA, como `@ManyToOne`, `@OneToMany` e `@ManyToMany`.

---

## 📋 Validação

A API utiliza **Jakarta Validation** para validar os dados recebidos.

Entre as validações implementadas estão:

* Campos obrigatórios
* Valores positivos
* Tamanho máximo de campos
* Formato de e-mail
* Validação de CNPJ
* Validação de telefone
* Categoria obrigatória
* Produto obrigatório em movimentações

Exemplo:

```java
@NotNull(message = "Quantidade é obrigatória")
@Positive(message = "Quantidade deve ser positiva")
private Integer quantity;
```

---

## 🔄 Exemplo de movimentação

### Entrada

```http
POST /movements
```

```json
{
  "type": "ENTRADA",
  "quantity": 10,
  "product": {
    "id": 1
  }
}
```

A quantidade será adicionada ao estoque do produto.

### Saída

```http
POST /movements
```

```json
{
  "type": "SAIDA",
  "quantity": 3,
  "product": {
    "id": 1
  }
}
```

Antes de realizar a saída, a aplicação verifica se o produto possui quantidade suficiente em estoque.

---

## 🌐 Principais endpoints

### Products

| Método   | Endpoint         | Descrição                 |
| -------- | ---------------- | ------------------------- |
| `GET`    | `/products`      | Lista e pesquisa produtos |
| `GET`    | `/products/{id}` | Busca produto por ID      |
| `POST`   | `/products`      | Cadastra produto          |
| `PUT`    | `/products/{id}` | Atualiza produto          |
| `DELETE` | `/products/{id}` | Remove produto            |

A consulta de produtos permite utilizar parâmetros como `name` e `id`.

Exemplo:

```http
GET /products?name=Notebook
```

### Movements

| Método | Endpoint          | Descrição                 |
| ------ | ----------------- | ------------------------- |
| `GET`  | `/movements`      | Lista movimentações       |
| `GET`  | `/movements/{id}` | Busca movimentação        |
| `POST` | `/movements`      | Registra entrada ou saída |

---

## 🗄️ Banco de dados

O projeto utiliza **MySQL** como banco de dados relacional.

Principais tabelas:

```text
categoria
produtos
fornecedor
produto_fornecedor
movimentacao
```

Os relacionamentos entre as entidades são mapeados utilizando **JPA/Hibernate**.

---

## ⚙️ Como executar o projeto

### 1. Clone o repositório

```bash
git clone https://github.com/MiguelSouza011/estoque.git
```

### 2. Entre no diretório

```bash
cd estoque
```

### 3. Configure o banco de dados

Crie um banco MySQL e configure as credenciais utilizadas pela aplicação.

Exemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/estoque
spring.datasource.username=root
spring.datasource.password=SUA_SENHA
```

> As configurações podem variar de acordo com o ambiente de desenvolvimento.

### 4. Execute a aplicação

Com Maven:

```bash
mvn spring-boot:run
```

Ou utilizando o Maven Wrapper:

**Windows:**

```bash
.\mvnw.cmd spring-boot:run
```

**Linux/macOS:**

```bash
./mvnw spring-boot:run
```

---

## 🧪 Testando a API

Depois de iniciar a aplicação, os endpoints podem ser testados utilizando ferramentas como:

* Postman
* Insomnia
* Bruno
* IntelliJ HTTP Client

Exemplo:

```http
GET http://localhost:8080/products
```

---

## 📚 Conceitos praticados

Durante o desenvolvimento deste projeto foram aplicados conceitos importantes de backend:

* Java
* Programação Orientada a Objetos
* Spring Boot
* APIs REST
* HTTP
* CRUD
* Spring Data JPA
* Hibernate
* ORM
* Relacionamentos entre entidades
* MySQL
* DTO
* MapStruct
* Lombok
* Jakarta Validation
* Tratamento de exceções
* Regras de negócio
* Streams e Lambdas
* Arquitetura em camadas
* Maven
* Git e GitHub

---

## 📌 Próximos passos

O projeto continua em desenvolvimento e pode receber novas funcionalidades, como:

* Documentação da API com OpenAPI/Swagger
* Paginação
* Ordenação e filtros mais avançados
* Testes unitários
* Testes de integração
* Docker e Docker Compose
* Autenticação e autorização
* Monitoramento
* Deploy em ambiente cloud

---

## 👨‍💻 Autor

**Miguel Souza**

Estudante de Engenharia de Software com foco em desenvolvimento backend utilizando **Java e Spring Boot**.

O projeto faz parte da minha jornada de estudos e tem como objetivo transformar conceitos aprendidos em aplicações práticas, aprofundando conhecimentos em desenvolvimento de APIs, bancos de dados e regras de negócio.

---

⭐ Se este projeto foi útil ou interessante para você, fique à vontade para deixar uma estrela no repositório.
