package com.fiap.receiver.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PÓS GRADUAÇÃO - FIAP 2025 - SERVIÇO DE RECEBIMENTO DE PEDIDOS")
                        .version("1.0.0")
                        .description("Microsserviço responsável por receber solicitações de novos pedidos, validar dados iniciais e encaminhar as requisições para o serviço de pedidos. Atua como ponto de entrada para integrações externas e garante a correta orquestração do fluxo de pedidos no sistema."));
    }
}