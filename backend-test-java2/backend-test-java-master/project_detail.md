# Projeto para vaga de desenvolvedor Back-end da FCamara

### Descrição do projeto
Criar uma API REST para gerenciar um estacionamento de carros e motos.

- Funcionalidades
	- CRUD do Estabelecimento;
	- CRUD dos Veículos;
	- Controle de entrada e saída de veículos.
	
### O projeto
- Tecnologias usadas:
	- Java Spring Boot, para criar a aplicação;
	- MySql, para persistência dos dados;
	- Lombok, para facilitar códigos boilerplates;
	- E a dependência: **jackson-dartaformat-xml**, para as respostas em xml.
	
- Como rodar a aplicação:
	- Requisitos para rodar o projeto em sua máquina:
		- O JDK mais recente, foi utilizado o JDK 17;
		- O Maven mais recente;
		- E o MySql mais recente.
		
	- Após fazer clone do projeto, acesse o arquivo *"src/main/resources/application.properties"*			
		- Mude os valores das seguintes propriedades, com suas credenciais do seu banco de dados
					MySql:
					
	```
	spring.datasource.username = SEU_USUÁRIO_DO_BANCO
	spring.datasource.password = SUA_SENHA
	```
	
	- Com as configurações do banco feitas, rode o seguinte comando do Maven para fazer build do projeto:
	
	```
	$ mvn clean package -U
	```
	
	- Se o build deu certo, um arquivo .jar será criado na pasta **"target"**, agora rode no terminal, o comando java:
	
	```
	$ java -jar target/backend-test-java-0.0.1-SNAPSHOT.jar
	```
	
	- se tudo deu certo, a aplicação deve estar rodando na url: *http://localhost:8080/api/v1/parking*
	
### Todos os endpoints estão documentados no arquivo "backend-api-collection.json", na pasta raiz do projeto, basta importá-lo no aplicativo [INSOMNIA](https://insomnia.rest/download).

# TODO

- [ ] Tratamento de erros;
- [ ] Limitar o cadastro das empresas pelo CNPJ;
- [ ] Limitar o cadastro de veículos pela placa do mesmo;
- [ ] Limitar a criação de tickets de estacionamento, pela existência de saída ou não, evitando mais de um ticket para o mesmo veículo;
