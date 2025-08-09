# receiver-service

# Serviço de Recebimento de Pedidos - Backend

## Introdução

Este microsserviço é parte integrante do sistema modular de gerenciamento de pedidos, 
atuando como ponto de entrada para o recebimento e validação inicial das solicitações de novos pedidos. 
Ele recebe os dados dos pedidos, valida as informações básicas e encaminha as requisições para o serviço de pedidos, 
garantindo a correta orquestração do fluxo de pedidos no sistema.

## Objetivo do Projeto

O objetivo principal deste microsserviço é fornecer uma API confiável para receber e validar pedidos, 
assegurando que os dados essenciais estejam presentes antes de encaminhar para processamento posterior. 
Atua como gateway para integrações externas, isolando a complexidade dos serviços internos e facilitando a escalabilidade e manutenção do sistema.

## Requisitos do Sistema

Para executar este microsserviço, você precisará dos seguintes requisitos:

- **Sistema Operacional**: Windows, macOS ou Linux
- **Memória RAM**: Pelo menos 4 GB recomendados
- **Espaço em Disco**: Pelo menos 500 MB de espaço livre
- **Software**:
    - Docker e Docker Compose
    - Java JDK 11 ou superior
    - Maven 3.6 ou superior
    - Kafka (para comunicação assíncrona)
    - Git

## Estrutura do Projeto

A estrutura do projeto está organizada da seguinte forma:
```plaintext
receiver-service/
│
├── src/
│ └── main/
│   ├── java/
│   │ └── com.fiap.receiver
│   │   ├── config/ : Configurações de segurança, Kafka e Swagger.
│   │   ├── controller/ : Controladores REST para endpoints de recebimento de pedidos.
│   │   ├── domain/ : Entidades de domínio do pedido recebido.
│   │   ├── dto/ : Objetos de transferência de dados (DTOs) para requisições.
│   │   ├── exception/ : Tratamento global de exceções.
│   │   ├── gateway/ : Interface e implementação para envio de pedidos via Kafka.
│   │   ├── mapper/ : Mapeamento entre DTOs e domínio.
│   │   ├── message/ : Configurações e beans para Kafka.
│   │   ├── usecase/ : Serviços de caso de uso para regras de negócio.
│   │   └── PedidoReceiverServiceApplication.java : Classe principal da aplicação.
│   └── resources/
│       └── application.properties : Configurações da aplicação.
├── pom.xml : Arquivo de configuração do Maven.
├── Dockerfile : Arquivo para construção da imagem Docker.
├── docker-compose.yml : Arquivo para orquestração de contêineres.
└── README.md : Documentação do projeto.
```

## Segurança

A segurança do microsserviço é configurada com Spring Security.

## Visão Geral do Projeto

Este microsserviço é desenvolvido com Spring Boot e segue uma arquitetura limpa simplificada, separando claramente as responsabilidades entre domínio,
casos de uso, persistência (via Kafka) e interface.

## Arquitetura

A arquitetura segue o padrão MVC e princípios da Arquitetura Limpa, com camadas bem definidas:

- **Domain**: Representa as entidades de negócio (PedidoReceiver).
- **UseCase**: Serviços que implementam regras de negócio e casos de uso.
- **Gateway**: Interface e implementação para envio de mensagens Kafka.
- **Controller**: Exposição dos endpoints REST para interação externa.
- **Mapper**: Conversão entre DTOs e domínio.

## Princípios de Design e Padrões de Projeto

### Princípios de Design

1. **Single Responsibility Principle (SRP)**:
    - Cada classe tem uma única responsabilidade, como o serviço de processamento ou o controlador REST.

2. **Open/Closed Principle (OCP)**:
    - As classes são abertas para extensão e fechadas para modificação, facilitando manutenção e evolução.

### Padrões de Projeto

1. **MVC (Model-View-Controller)**:
    - Controladores REST atuam como controladores.
    - Domínio representa o modelo.
    - Respostas JSON são a "view".

2. **Gateway Pattern**:
    - Abstrai o envio de mensagens Kafka, facilitando troca da implementação sem impactar o domínio.

3. **Mapper Pattern**:
    - Facilita a conversão entre diferentes camadas do sistema.

## Interação entre as Partes do Sistema

1. **Cliente/Integração Externa**: Envia requisições HTTP para criar novos pedidos.
2. **Controller**: Recebe as requisições e delega para os casos de uso.
3. **UseCase**: Executa validações e chama o gateway para encaminhar o pedido.
4. **Gateway**: Envia o pedido para o tópico Kafka para processamento posterior.
5. **Serviço de Pedido**: Consome as mensagens Kafka para processar os pedidos.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para desenvolvimento Java.
- **Spring Security**: Segurança da aplicação.
- **Spring Kafka**: Comunicação assíncrona via Kafka.
- **Kafka**: Broker de mensagens para integração entre microsserviços.
- **MapStruct**: Mapeamento entre objetos.
- **Lombok**: Redução de boilerplate.
- **Swagger (Springdoc OpenAPI)**: Documentação da API.
- **Docker e Docker Compose**: Containerização e orquestração.

## Pré-requisitos

Antes de executar o microsserviço, certifique-se de ter instalado:

- Docker e Docker Compose
- Java JDK 11 ou superior
- Maven 3.6 ou superior
- Kafka rodando localmente ou via container

### Passos para Executar o Docker Compose

1. Certifique-se de que o Docker e o Docker Compose estejam instalados e rodando na sua máquina.
2. No terminal, navegue até o diretório onde está localizado o arquivo `docker-compose.yml`.
3. Execute o seguinte comando para iniciar os contêineres:
   ```bash
   docker compose up
   ```
4. A aplicação estará disponível em `http://localhost:8087` e o Swagger em `http://localhost:8087/swagger-ui/index.html#/`.
5. O banco de dados PostgreSQL estará rodando em `http://localhost:5432`.
6. A ferramenta Adminer estará disponível para visualização do banco de dados no endereço `http://localhost:8088`.

### Passos para Conectar no Banco de Dados com o Adminer

1. Acesse o endereço `http://localhost:8088`.
2. Em Sistema, escolha PostgreSQL.
3. Em Servidor, preencha o nome do serviço do Postgres do Docker Compose (postgres).
4. Em Usuário, preencha postgres.
5. Em Senha, preencha postgres.
6. Em Base de dados, preencha com postgres.
7. Clique em Entrar.


## Endpoints Principais

- `POST /api/pedidos` - Receber novo pedido

## Contribuição

Contribuições são bem-vindas! Para contribuir:

1. Faça um fork do repositório.
2. Crie uma branch para sua feature (`git checkout -b feature/nome-da-feature`).
3. Faça commit das suas alterações (`git commit -m 'Descrição da feature'`).
4. Envie para o repositório remoto (`git push origin feature/nome-da-feature`).
5. Abra um Pull Request.

## Licença

Este projeto é privado ou não possui licença específica.

## Referências e Recursos

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [Spring Kafka Documentation](https://spring.io/projects/spring-kafka)
- [Kafka Documentation](https://kafka.apache.org/documentation/)
- [MapStruct Documentation](https://mapstruct.org/documentation/stable/reference/html/)
- [Swagger OpenAPI Documentation](https://springdoc.org/)

## Conclusão

Este microsserviço de recebimento de pedidos exemplifica a aplicação de arquitetura limpa e boas práticas no desenvolvimento de microsserviços, 
garantindo modularidade, escalabilidade e facilidade de manutenção dentro do sistema de gerenciamento de pedidos.
