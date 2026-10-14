# 📦 Estoque API

API REST para gerenciamento de estoque desenvolvida com **Java e Spring Boot**, com foco em boas práticas de desenvolvimento backend, segurança, regras de negócio e integração com serviços de e-mail.

O sistema permite gerenciar produtos, categorias, fornecedores e movimentações de estoque, além de controlar o acesso dos usuários por meio de autenticação JWT e enviar alertas automáticos quando produtos atingem níveis baixos de estoque.

## 🎯 Objetivo

Desenvolver uma API REST para gerenciamento de estoque, aplicando conceitos utilizados no desenvolvimento de aplicações backend, como arquitetura em camadas, persistência de dados, validação, tratamento de exceções, autenticação, autorização e integração com serviços externos.

## 🚀 Funcionalidades

### 📦 Produtos

- Cadastro, consulta, atualização e exclusão de produtos.
- Busca de produtos por identificador e nome.
- Associação de produtos a categorias.
- Associação de produtos a fornecedores.
- Controle da quantidade disponível em estoque.
- Consulta de produtos com estoque baixo.

### 🗂️ Categorias

- Cadastro de categorias.
- Consulta de categorias e busca por identificador.
- Atualização e exclusão de categorias.
- Associação de categorias aos produtos.

### 🏢 Fornecedores

- Cadastro, consulta, atualização e exclusão de fornecedores.
- Validação de CNPJ.
- Associação de fornecedores aos produtos.

### 🔄 Movimentações de estoque

O sistema permite registrar movimentações de entrada e saída de produtos.

Tipos de movimentação:

- `ENTRADA`: adiciona unidades ao estoque.
- `SAIDA`: retira unidades do estoque.

As movimentações registram informações como tipo, quantidade, data e produto relacionado. Nas saídas, a aplicação verifica se existe quantidade suficiente para evitar estoque negativo.

### 🔐 Autenticação e autorização

A API utiliza **Spring Security e JWT (JSON Web Token)** para autenticar usuários e proteger os endpoints.

Funcionalidades de segurança:

- Autenticação de usuários.
- Geração e validação de tokens JWT.
- Senhas armazenadas com hash BCrypt.
- Controle de acesso baseado em roles.
- Restrição de operações administrativas.
- Proteção de endpoints por autenticação e autorização.

As operações de cadastro, atualização e exclusão de produtos, categorias e fornecedores são restritas a usuários com a role `ADMIN`, conforme as regras configuradas na aplicação.

O cadastro público de usuários deve criar contas com a role `USER`. A criação de contas administrativas deve ser restrita a administradores autorizados.

### 📧 Alertas automáticos por e-mail

A aplicação possui integração com SMTP para envio de notificações por e-mail.

O sistema verifica periodicamente os produtos com estoque baixo e envia uma notificação contendo os produtos que precisam de reposição.

Funcionalidades:

- Verificação automática dos níveis de estoque.
- Identificação de produtos abaixo do limite configurado.
- Envio de alertas por e-mail.
- Configuração do destinatário e das credenciais por variáveis de ambiente.

O limite de estoque baixo é configurável. Na configuração atual de desenvolvimento, o limite utilizado é de 10 unidades.

### ⚠️ Validação e tratamento de exceções

A API utiliza Jakarta Validation para validar os dados recebidos e exceções personalizadas para tratar situações de negócio.

Entre os recursos implementados estão:

- Validação de campos obrigatórios e formatos.
- Tratamento de recursos não encontrados.
- Tratamento de violações de regras de negócio.
- Respostas HTTP adequadas aos erros tratados.
- Centralização do tratamento de exceções com `@RestControllerAdvice`.

## 🛠️ Tecnologias utilizadas

- **Java 25**
- **Spring Boot 4.1.1**
- Spring Web MVC
- Spring Data JPA
- Hibernate
- MySQL
- Spring Security
- JWT
- BCrypt
- Spring Boot Starter Mail / JavaMailSender
- Jakarta Validation
- Lombok
- MapStruct
- Maven
- Git e GitHub
- Postman

## 🏗️ Arquitetura

O projeto utiliza arquitetura em camadas para separar as responsabilidades e facilitar a manutenção.

```text
src/main/java/com/miguelsouza/estoque
├── controller
├── service
├── repository
├── entities
├── dto
├── mapper
├── exceptions
└── config
```

### Controller

Recebe as requisições HTTP, encaminha as operações para os serviços e retorna as respostas da API.

### Service

Concentra as regras de negócio, como validação de operações, movimentações de estoque e verificação de produtos com estoque baixo.

### Repository

Responsável pelo acesso aos dados utilizando Spring Data JPA.

### Entities

Representam as entidades persistidas no banco de dados, como produtos, categorias, fornecedores, movimentações e usuários.

### DTOs e mapeamento

Os DTOs definem os dados utilizados nas requisições e respostas. O MapStruct auxilia no mapeamento entre objetos, reduzindo código repetitivo.

### Exceptions

Centraliza as exceções personalizadas e o tratamento de erros da aplicação.

### Segurança

O Spring Security controla a autenticação e a autorização das requisições, enquanto o filtro JWT identifica os usuários autenticados.

## 🔗 Relacionamentos entre entidades

### Produto e categoria

Um produto pertence a uma categoria.

```text
Category 1 ──────── N Product
```

### Produto e fornecedor

Um produto pode estar associado a vários fornecedores, e um fornecedor pode fornecer vários produtos.

```text
Product N ──────── N Supplier
```

Esse relacionamento utiliza uma tabela intermediária no banco de dados.

### Produto e movimentações

Um produto pode possuir várias movimentações de estoque.

```text
Product 1 ──────── N Movement
```

Os relacionamentos são mapeados utilizando anotações do JPA, como `@ManyToOne`, `@OneToMany` e `@ManyToMany`.

## 🌐 Principais endpoints

Os endpoints abaixo representam as rotas principais da aplicação. As permissões devem ser conferidas na configuração de segurança.

### Autenticação

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/auth/login` | Autenticação do usuário |
| POST | `/auth/register` | Cadastro público de usuário |

Após a autenticação, utilize o token JWT nas requisições protegidas, no cabeçalho:

```http
Authorization: Bearer SEU_TOKEN_JWT
```

### Produtos

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/products` | Listar e pesquisar produtos |
| GET | `/products/{id}` | Buscar produto por ID |
| POST | `/products` | Cadastrar produto |
| PUT | `/products/{id}` | Atualizar produto |
| DELETE | `/products/{id}` | Excluir produto |

### Categorias

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/categories` | Consultar categorias |
| POST | `/categories` | Cadastrar categoria |
| PUT | `/categories/{id}` | Atualizar categoria |
| DELETE | `/categories/{id}` | Excluir categoria |

### Fornecedores

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/suppliers` | Consultar fornecedores |
| POST | `/suppliers` | Cadastrar fornecedor |
| PUT | `/suppliers/{id}` | Atualizar fornecedor |
| DELETE | `/suppliers/{id}` | Excluir fornecedor |

### Movimentações

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/movements` | Consultar movimentações |
| GET | `/movements/{id}` | Buscar movimentação por ID |
| POST | `/movements` | Registrar entrada ou saída |

**Observação:** confirme os métodos e caminhos diretamente nos controllers antes de considerar esta tabela como uma especificação completa da API. O endpoint de alerta de estoque baixo também deve ser documentado conforme a rota realmente implementada.

## 🗄️ Banco de dados

O projeto utiliza MySQL como banco de dados relacional e Spring Data JPA com Hibernate para persistência.

As principais entidades incluem:

- Produtos.
- Categorias.
- Fornecedores.
- Movimentações.
- Usuários.

As credenciais do banco de dados não devem ser armazenadas diretamente no código-fonte. Configure-as de acordo com o ambiente de execução.

## ⚙️ Como executar o projeto

### 1. Pré-requisitos

- JDK compatível com Java 25.
- MySQL instalado e em execução.
- Maven ou Maven Wrapper.
- Git.

### 2. Clone o repositório

```bash
git clone https://github.com/MiguelSouza011/estoque.git
cd estoque
```

### 3. Configure o banco de dados

Crie o banco de dados utilizado pela aplicação e configure a URL, o usuário e a senha nas propriedades ou variáveis de ambiente correspondentes ao seu projeto.

Exemplo de configuração:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/estoque
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

Ajuste os nomes das propriedades conforme a configuração efetiva da aplicação.

### 4. Configure o envio de e-mails

Para utilizar o SMTP do Gmail, configure as variáveis de ambiente necessárias:

```text
MAIL_USERNAME=seu_email@gmail.com
MAIL_PASSWORD=sua_senha_de_aplicativo
STOCK_ALERT_RECIPIENT=email_destinatario@example.com
```

Utilize uma senha de aplicativo do Google quando necessário. Não publique senhas, tokens JWT ou outras credenciais no repositório.

Configure também a variável de ambiente utilizada para o segredo JWT, de acordo com o `application.yml` da aplicação.

### 5. Execute a aplicação

No Windows, utilizando o Maven Wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

A aplicação utiliza a porta configurada no projeto, normalmente `8080`.

## 🧪 Testando a API

Utilize Postman, Insomnia, Bruno ou o cliente HTTP do IntelliJ para testar os endpoints.

Recomenda-se validar os seguintes cenários:

- Login com credenciais válidas e inválidas.
- Acesso a rotas protegidas com e sem JWT.
- Tentativa de operação administrativa com um usuário `USER`.
- Cadastro de usuários e controle de roles.
- Operações CRUD de produtos, categorias e fornecedores.
- Entradas e saídas de estoque.
- Tentativa de retirada de quantidade superior à disponível.
- Validação de dados inválidos.
- Busca de recursos inexistentes.
- Envio manual de e-mail, se o endpoint de teste estiver habilitado.
- Envio automático de alertas quando houver produtos com estoque baixo.

## 📚 Conceitos praticados

Durante o desenvolvimento foram trabalhados conceitos importantes de desenvolvimento backend:

- Java e Programação Orientada a Objetos.
- Spring Boot e APIs REST.
- Arquitetura em camadas.
- Spring Data JPA e Hibernate.
- Mapeamento objeto-relacional.
- DTOs e MapStruct.
- Lombok.
- Jakarta Validation.
- Tratamento global de exceções.
- Regras de negócio.
- Relacionamentos entre entidades.
- Spring Security.
- Autenticação JWT.
- Autorização baseada em roles.
- Hash de senhas com BCrypt.
- Integração SMTP para envio de e-mails.
- Agendamento de tarefas.
- Maven.
- Git e GitHub.
- Testes manuais de APIs com Postman.

## 👨‍💻 Autor

**Miguel Souza**

Estudante de Engenharia de Software com foco em desenvolvimento backend utilizando Java e Spring Boot.

Este projeto faz parte da minha jornada de aprendizado em desenvolvimento de software e demonstra a aplicação prática de conceitos de APIs REST, persistência de dados, segurança, regras de negócio e integração com serviços externos.

---

⭐ Se este projeto foi útil ou interessante, fique à vontade para deixar uma estrela no repositório.
