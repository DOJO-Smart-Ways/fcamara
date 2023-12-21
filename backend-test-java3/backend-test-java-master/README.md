# API de Estacionamento

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/>
  <img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white"/>
  <img src="http://img.shields.io/static/v1?label=TESTES&message=%3E100&color=GREEN&style=for-the-badge"/>
   <img src="http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=RED&style=for-the-badge"/>
   
</p>
<br>
Está API foi desenvolvida para testar os meus conhecimentos sobre back-end e me auxiliar a praticar durante os meus estudos em Java e Spring-Boot, com certeza ela não está perfeita mas estou em constante evolução e desenvolvimento, aceito dicas e melhorias para o código ou formas diferentes que eu poderia ter desenvolvido ele.<br>
O propósito desta API é realizar o controle de um estacionamento, possuindo as funcionalidades citadas abaixo.<br>
- Status do Projeto: 🔧 Em Desenvolvimento

### ⚙️ Funcionalidades


A API de Estacionamento possuí as seguintes funcinalidades:

- Cadastro de estabelecimentos.
- Cadastro de veículos.
- Controle de entrada e saída de veículos.

### 📚 Documentação

Está API possuí uma documentação com suas rotas, pórem para acessala é preciso inicializar a aplicação pois ainda não possuí um link on-live.<br>
Após iniciar a aplicação basta acessar o seguinte link para visualizar a documentação:<br>
[Swagger Docs](http://localhost:8080/swagger-ui.html) - **http://localhost:8080/swagger-ui.html**

### 📝Topicos


- [Começando](#-começando)

- [Como rodar](#%EF%B8%8F-como-rodar)

- [Desenvolvimento](#-desenvolvimento)

- [Autores](#%EF%B8%8F-autores)

## 🚀 Começando

Primeiramente é preciso clonar o projeto na sua máquina, para isso, abra o git em algum terminal e rode o seguinte comando:
```
$ git clone https://github.com/henrique-leme/backend-test-java.git
```

### 📋 Pré-requisitos

Para rodar e instalar o projeto é preciso ter instalado
- Java 8
- Postgres
- Insomnia ou Postman
- Git 
- IDE de sua escolha ( recomendo Intellij ou VS Code )

### 🔧 Instalação

Tendo os requisitos acima você ira abrir o projeto na sua IDE.

- No caso do VS Code, é recomendado que você tenha o ambiente Java configurado, recomendo seguir os passos no vídeo do link abaixo até os 5:20 minutos de vídeo, após isso podera abrir o projeto no VS Code normalmente

(https://www.youtube.com/watch?v=K2OD-3NBE6s&ab_channel=HélioKamakawa) 

- Para abrir o projeto no Intellij, ele ira automaticamente detectar que é um projeto Maven e ira configurar as dependencias, caso tenha algum problema e dificuldade, consulte o link abaixo

(https://www.jetbrains.com/help/idea/import-project-or-module-wizard.html#export-project)
(https://www.jetbrains.com/idea/guide/tutorials/working-with-maven/importing-a-project/)




### 🔌Iniciando/Configurando banco de dados

Para rodar o projeto propriamente, é preciso configurar o banco de dados de acordo com o seu ambiente, para isso vá até o arquivo **application.properties** e altere as seguintes linhas:

```
# Aqui você ira criar um banco de dados postgre com o nome (api_estacionamento)
spring.datasource.url=jdbc:postgresql://localhost:5432/api_estacionamento

# coloque nesta linha seu usuario do banco de dados no local de (seu_nome_de_usuario) ou utilize um com o nome padrão do projeto (postgres)
spring.datasource.username=seu_nome_de_usuario

# coloque nesta linha a sua senha do banco de dados ou utilize a padrão do projeto (postgres)
spring.datasource.password=sua_senha 
```

## ⚙️ Como rodar

Após configurar e iniciar seu banco de dados, navegue até o arquivo **RestapiApplication** para rodar o projeto

```
restapi/src/main/java/com/estacionamento/restapi/RestapiApplication.java
```

### 🛠 Como testar os end-points

Para rodar os endpoints e testar as rotas, você pode importar as configurações no diretório do repositório e testar as requisições no Insominia ou no Postman ( recomendado o Insomnia )

- [Json Model]()
- [Har Model]()


## 📦 Desenvolvimento

Para implementar está API em um sistema ativo, basca conectala a um banco de dados e utilizar os end-points da API

### 🛠️ Construído com

Para o desenvolvimento deste projeto, foram utilizadas as seguintes ferramentas

- [Intellij IDEA](https://www.jetbrains.com/pt-br/idea/) - O ambiente de desenvolvimento
- [Maven](https://maven.apache.org/) - Gerente de Dependência
- [Spring Boot](https://spring.io/projects/spring-boot) - Framework utilizado
- [PostgreSQL](https://www.postgresql.org) - Banco de dados utilizado

### 📌 Versão
Este projeto está em sua primeira versão e está em constante desenvolvimento, caso ache algum Bug, relate ao Desenvolvedor do projeto

## ✒️ Autores

Abaixo os autores do projeto, Henrique que desenvolveu e Rodrigo que instruiu  ao desenvolvimento dando feedbacks para melhorias do mesmo.
- **Henrique Leme de Brito** - _Desenvolvedor_ - [Git-Hub](https://github.com/henrique-leme)
- **Rodrigo Rodrigues** - _Project Leader_ 

