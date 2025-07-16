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

## Como Executar o Projeto

1. Clone o repositório:
   ```bash
   git clone <url-do-repositorio>
   ```
2. Navegue até o diretório do microsserviço receiver-service:
   ```bash
   cd receiver-service
   ```
3. Compile e empacote o projeto com Maven:
   ```bash
   mvn clean package -DskipTests
   ```
4. Configure o Kafka conforme `application.properties`.
5. Execute a aplicação localmente:
   ```bash
   mvn spring-boot:run
   ```
6. Ou utilize Docker Compose para subir o serviço e Kafka:
   ```bash
   docker compose up
   ```
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
