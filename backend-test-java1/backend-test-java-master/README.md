
# API de estacionamento para Back-end test Java Fcamara

Esta API foi criada para um teste da empresa Fcamara. Link: https://github.com/fcamarasantos/backend-test-java

Ela Usa um banco de dados mySQL e Java para a integração. Foi criada usando o 
intelliJ IDEA, com BrModelo para criação dos modelos conceitual e lógico do banco, mais o Postman para testes das requisições HTTP.

O corpo das requisições e respostas está em JSON.

## Modelos do banco

#### Conceitual:

![App Screenshot](https://github.com/GustavoJPereira/backend-test-java/blob/master/extras/Modelo%20Conceitual.png?raw=true)

#

#### Lógico:

![App Screenshot](https://github.com/GustavoJPereira/backend-test-java/blob/master/extras/Modelo%20L%C3%B3gico.png?raw=true)
## Stack utilizada

**Dados:** mySQL

**Back-end:** Java, Marven, SpringBoot


## Instalação

Para instalar o projeto, use o arquivo  [**EstacionamentoFcamara.sql**](extras/EstacionamentoFcamara.sql) no mySQL para criar 
o banco de dados.
Em seguida, em **src/main/resources** edite o arquivo **application.properties** na linha
2, substituindo a palavra **root** pelo root do mySQL e na linha 3, substituindo a palavra
**senha** pela senha do root.
    
## Documentação da API

A API pode responder as solicitações para três entidades:

* **Empresa**
* **Veículo**
* **Relatorio**

A seguir as requisições possíveis. Os Parâmetro que não estiverem marcados como **URL**
são passados no corpo da requisição.

Todas as requisições devem ser feitas pela caminho **http://localhost:8080**.

#### Exemplo:
``` http
    PUT http://localhost:8080/empresa/3
```

``` JSON
{
    "cnpj": 11122233344455,
    "nome": "empresa A",
    "telefone": "4444-4444",
    "vagas_carro": 3,
    "vagas_moto": 4
}
```

Os métodos GET permitem a inserção de sort para ordenar a pesquisa. Exemplo:

``` HTTP
GET http://localhost:8080/empresa?sort=name,desc
```

Use '?' no final da url seguido da coluna que deseja filtrar mais a ordem que pode ser asc(ascendente) 
ou desc(descendente).

Você pode decidir como será feita a paginação com sort 'size' para a quantidade de entidades 
e 'page' para decidir a página que receberá.

Para usar mais de um destes os separe usando um '&' ('e' comercial) ;

Exemplo:

``` HTTP
GET http://localhost:8080/empresa?size=10&page=0;
```

### **Empresa**

A empresa na qual o estacionamento está.

#### Retorna todos os itens

```http
  GET /empresa
```


#### Insere um novo item no banco
```http
  POST /empresa
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `nome`      | `String` | **Obrigatório**. O nome da empresa. |
| `cnpj`      | `String` | **Obrigatório**. O CNJP da empresa. |
| `telefone`  | `Long` | **Obrigatório**. O telefone da empresa. |
| `vagas_moto` | `int` | **Obrigatório**. Quantidade de vagas para motos no estacionamento. |
| `vagas_carro` | `int` | **Obrigatório**. Quantidade de vagas para carros no estacionamento. |



#### Atualiza um item no banco
```http
  PUT /empresa/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `Long` | **Obrigatório**, **URL**. O ID do item desejado. |
| `nome`      | `String` | **Obrigatório**. O nome da empresa. |
| `cnpj`      | `String` | **Obrigatório**. O CNJP da empresa. |
| `telefone`  | `Long` | **Obrigatório**. O telefone da empresa. |
| `vagas_moto` | `int` | **Obrigatório**. Quantidade de vagas para motos no estacionamento. |
| `vagas_carro` | `int` | **Obrigatório**. Quantidade de vagas para carros no estacionamento. |


#### Retira um item do banco
```http
  DELETE /empresa/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `Long` | **Obrigatório**, **URL**. O ID do item desejado. |


### **Veículo**

O veículo (moto ou carro) que irá estacionar.

#### Retorna todos os itens

```http
  GET /veiculo
```

#### Insere um novo item no banco
```http
  POST /veiculo
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `marca`      | `String` | **Obrigatório**. A marca do veículo. |
| `cor`      | `String` | **Obrigatório**. A cor do veículo. |
| `placa`  | `String` | **Obrigatório**. A placa do veículo. |
| `tipo` | `String` | **Obrigatório**. Tipo de veículo. Só aceito **moto** ou **carro** |
| `modelo` | `String` | **Obrigatório**. modelo do veículo. |



#### Atualiza um item no banco
```http
  PUT /veiculo/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `Long` | **Obrigatório**, **URL**. O ID do item desejado. |
| `marca`      | `String` | **Obrigatório**. A marca do veículo. |
| `cor`      | `String` | **Obrigatório**. A cor do veículo. |
| `placa`  | `String` | **Obrigatório**. A placa do veículo. |
| `tipo` | `String` | **Obrigatório**. Tipo de veículo. Só aceito **moto** ou **carro**. |
| `modelo` | `String` | **Obrigatório**. modelo do veículo. |


#### Retira um item do banco
```http
  DELETE /veiculo/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `Long` | **Obrigatório**, **URL**. O ID do item desejado. |


### **Relatório**

Relatório que relaciona os veículos a empresa. Com registro de quando entraram e saíram (ou vão sair). 

#### Retorna todos os itens

```http
  GET /relatorio
```

#### Insere um novo item no banco
```http
  POST /relatorio
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `empresa_id`      | `Long` | **Obrigatório**. O ID da empresa em que o veículo está/estava (Somente possível usar IDs existentes no banco). |
| `veiculo_id`      | `String` | **Obrigatório**. O ID do veículo que foi estacionado (Somente possível usar IDs existentes no banco). |
| `entrada`  | `String` | **Obrigatório**. O horário de entrada do veículo. |
| `saida` | `String` | O horário de saída do veículo. |



#### Atualiza um item no banco
```http
  PUT /relatorio/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `Long` | **Obrigatório**, **URL**. O ID do item desejado. |
| `empresa_id`      | `Long` | **Obrigatório**. O ID da empresa em que o veículo está/estava (Somente possível usar IDs existentes no banco). |
| `veiculo_id`      | `String` | **Obrigatório**. O ID do veículo que foi estacionado (Somente possível usar IDs existentes no banco). |
| `entrada`  | `String` | **Obrigatório**. O horário de entrada do veículo. |
| `saida` | `String` | O horário de saída do veículo. |


#### Retira um item do banco
```http
  DELETE /relatorio/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `Long` | **Obrigatório**, **URL**. O ID do item desejado. |



## Feedback

Se você tiver algum feedback, eu gostaria de receber pelo linkedin https://linkedin.com/in/gustavojpereira/

ou pelo e-mail gustavojosepereira2021@gmail.com

