# Desafio técnico Wirecard

Autor: Rafael Kawahara

Desafio feito como descrito no link: https://github.com/wirecardBrasil/challenge versão do commit (a827b20)

Este documento descreve apenas o serviço feito para servir a API.

## Problema

Devemos criar uma API que simule um pagamento, que receba as informações de um pagamento, salve suas informações e retorne se sucesso ou falha.

1. Se o pagamento for boleto, retornamos um número do boleto.
2. Se for Cartão de Crédito retornamos se sucesso ou falha.

## Arquitetura e Desenho da Solução

Para este problema, construí um projeto nos padrões do Clean Archtecture, como descrito no link: https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html.

Mais precisamente, esta imagem é importante:

![Clean Archtecture](https://blog.cleancoder.com/uncle-bob/images/2012-08-13-the-clean-architecture/CleanArchitecture.jpg)

Ao abrir o projeto, você vai encontrar módulos separados das Entities, Use Cases, Controllers, Repositórios, etc.

As camadas de dentro, não tem acesso as de fora, então por exemplo as Entities estão no nível mais interno, modelando o problema e resolvendo problemas de negócio. Essas camadas se conversam por interfaces quando preciso, assim, a implementação delas pode ser facilmente trocada.

### Linguagem

Para esta solução escolhi Kotlin, por rodar na JVM e conseguir fazer uso de vários Frameworks disponíveis para o ecossistema Java.

Por ser uma linguagem mais funcional, utilizei os recursos que ela dispõe para não manter estado nos objetos, tornando assim o código thread-safety. Podemos ter várias execuções em paralelo sem se preocupar com problemas de concorrência.

### Entities

Onde o problema é modelado. Neste caso, apenas algumas regras de negócio foram implementadas, como por exemplo a validação de cartão de crédito.

Esta validação do Cartão de crédito foi baseada na biblioteca do wirecard (https://github.com/wirecardBrasil/credit-card-validator).

Foi criado aqui um conceito um pouco maior, com ordem de pagamento, assim, cada processamento é uma ordem nova. A entidade Order representa isso, com todas as outras entidades (Client, Buyer e Payment) mais um Id de processamento e a data.

### Use Cases

Os Use Cases devem ser fluxos bem fáceis de se ler, assim como por exemplo o processamento de ordem (ProcessOrder).

Criei uma interface dentro do Use Cases para interação com o repositório, já que esta camada não deve se preocupar com a implementação, apenas que a função de procurar ou salvar está sendo chamada.

Assim, o processamento de uma ordem ficaria:

1. Salvar a ordem assim que chegar
2. Definir o tipo de pagamento
3. Validar o objeto de Pagamento
4. Salvar status desta validação
5. Retornar caso seja inválido
6. Processar pagamento
7. Salvar status pagamento
8. Retornar o resultado

Isso se reflete no Use Case:

```
fun process(paymentOrder: PaymentOrder) : ProcessOrderOutput {

    orderRepository.saveOrder(paymentOrder)

    val paymentType = paymentOrder.buildPaymentStrategy()

    val paymentValidReturn = paymentType.validatePayment(paymentOrder)

    orderRepository.saveOrderStatus(paymentValidReturn.toPaymentOrderStatus(paymentOrder.id))

    if(paymentValidReturn.status != OrderStatus.VALID) {
        return paymentValidReturn
    }

    val result = paymentType.runPayment(paymentOrder)

    orderRepository.saveOrderStatus(result.toPaymentOrderStatus(paymentOrder.id))

    return result
}
```

Concluindo, Use Cases não deve ter lógica de negócios explicita, apenas chamadas a funções que resolvem isso. Assim, o código fica limpo e fácil de se ler.

#### Estratégia de Pagamento

Para definir a estratégia de pagamento, utilizei do padrão de projeto Strategy, com a interface PaymentStrategy, as duas classes que implementam essas interfaces BoletoPayment e CreditCardPayment e com a classe PaymentStrategyFactory retornando uma dessas duas implementações baseado no tipo de pagamento a ser feito.

### MySqlRepository

A implementação escolhida para o repositório foi a Mysql com jdbi, não utilizei hibernate por considerar um pouco pesado para este projeto. Jdbi tem uma interface mais leve e direta com o banco, dando mais controle com o que estamos fazendo.

Foi criado testes de integração utilizando o banco em memória h2.

O legal da separação das camadas é que o modelo de banco de dados pode ser complemetamente diferente do seu modelo de entidades. Aqui, optei por uma estrutura mais flat da ordem, deixando todos os elementos em uma só tabela, separando apenas os Status. Assim, uma ordem pode ter vários Status.

Nessa implementação, deixamos de fazer vários Joins na procura de uma Ordem de Processamento.

### Controllers

Para a camada de exposição das APIs foi utilizada o PIPPO, que facilita controle de rotas, parseamento do corpo da requisição para um objeto e exposição das APIs.

Assim como na camada de repositório, é legal criar modelos de input e output diferentes das entidades, assim sua api fica com o contrato de entrada/saída bem definidos, não precisando mudar quando alguma regra de negócio da entidade muda.

Para fazer a conversão do input para as entidades de negócio, delegamos a classes especializadas que fazem isso de forma inteligente. São essas as classes que de forma centralizada vão ter a inteligência caso as entidades mudem, deixando as apis com o mesmo contrato.

### Testes

Foram feitos testes unitários em todas as camadas, mas não com cobertura total. Julguei que para este teste, não seria necessário fazer testes para tudo.

### O que falta

1. Testes unitário em todas com cobertura maior
2. Validação completa de todas as classes do modelo, como por exemplo:
    - Validação de email
    - Validação de cpf
    - Etc.
3. Tornar o código mais resiliente com:
    - Verificação de falhas, em chamadas ao banco de dados por exemplo
    - Logs da aplicação
    - Etc.

### Como rodar

- Baixar o projeto do Github
- Ter java 1.8 no PATH
- Ter gradle no PATH

#### Mysql

- Criar banco de dados Mysql se não tiver ainda.
- Aconselho rodar em um container docker com o seguinte comando:
``` 
docker run --restart unless-stopped -p 3306:3306 --name mysql1 -e MYSQL_ALLOW_EMPTY_PASSWORD=true -dit mysql:5.7
```
- Instala a versão 5.7, com senha do sa como vazia, mapeando a porta 3306 para o serviço.
- Para criar a base, rode o script:
``` 
create database rikchallenge;
```
- E depois:
``` 
use rikchallenge;

CREATE TABLE payment_order (
    id                VARCHAR (50)           PRIMARY KEY,
    client_id         VARCHAR (255)                     ,
    buyer_name        VARCHAR (255)                     ,
    buyer_email       VARCHAR (255)                     ,
    buyer_cpf         VARCHAR (15)                      ,
    payment_amount    DOUBLE                            ,
    payment_type      VARCHAR (50)                      ,
    card_holder_name  VARCHAR (255)                     ,
    card_number       VARCHAR (16)                      ,
    card_expiration_date VARCHAR (8)                    ,
    card_cvv          VARCHAR (4)                       ,
    order_date        DATETIME
);

CREATE INDEX payment_order_clientId_buyerCpf
ON payment_order(client_id,buyer_cpf);

CREATE TABLE payment_order_status (
    id                VARCHAR (50)           PRIMARY KEY,
    order_id          VARCHAR (50)                      ,
    order_status      VARCHAR (50)                      ,
    payload_type      VARCHAR (50)                      ,
    payload           VARCHAR (255)                     ,
    status_date       DATETIME                          ,
    FOREIGN KEY (order_id) REFERENCES payment_order(id)
);
```

#### Gradle

- Para fazer o build do projeto, digite no console do SO:
``` 
gradle clean build
```
- Rodar os testes:
``` 
gradle clean test
```
- Rodar o projeto (porta 8181):
``` 
gradle clean run
```
#### Docker

Se quiser rodar o projeto no docker ao invés do gradle, depois de ter feito o build, rode o seguinte:
``` 
docker run --name 'dockerhost' --cap-add=NET_ADMIN --cap-add=NET_RAW --restart on-failure -d qoomon/docker-host
```
Comando do dokerhost, assim os aplicativos conseguem se achar dentro do docker sem precisar do endereço de ip físico deles.

Na pastas raiz do projeto:
``` 
docker build -t rikchallenge -f .\docker\Dockerfile .
```
Para criar a imagem. E:
```
docker run --name rikchallenge1 --link 'dockerhost' -p 8181:8181 -dit rikchallenge 
```
Para criar o container e rodar. 

### Testes

O serviço estará rodando no endereço: http://localhost:8181.

Para testar, importe o arquivo rikchallenge.postman_collection.json no postman.

Exemplo de teste do boleto:

Url: http://localhost:8181/order/process  
Método: Post  
Headers: [Content-Type, application/json]  
Body:
```
{
	"client": {
		"id": "client1"
	},
	"buyer": {
		"name": "name1",
		"eMail": "e2@mail.com",
		"cpf": "12344567890"
	},
	"payment" : {
		"amount": 200.3,
		"type": "BOLETO"
	}
}
```

Resposta:
```
{
    "processOrderId": "5ad30c70-af4e-4b4c-a2df-6036a6d96991",
    "status": "SUCCESS",
    "payloadType": "BOLETO_NUMBER",
    "payload": "02734004867782157111"
}
```

Nota: Pelo payloadType, eu sei que o que foi gerado é o número de boleto, e o payload em si é o número do boleto.


### Dúvidas

Por favor, se virem que está faltando algo, ou se ficaram em dúvida com alguma implementação, framework, etc, me procurem!!