package com.fiap.receiver.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OpenApiConfigTest {

    @InjectMocks
    private OpenApiConfig openApiConfig;

    private OpenAPI openAPI;

    @BeforeEach
    void setUp() {
        openAPI = openApiConfig.customOpenAPI();
    }

    @Test
    void deveRetornarOpenApiNaoNulo() {
        assertNotNull(openAPI);
    }

    @Test
    void deveRetornarOpenApiComInfoNaoNula() {
        Info info = openAPI.getInfo();
        assertNotNull(info);
    }

    @Test
    void deveConfigurarTituloCorretamente() {
        String tituloEsperado = "PÓS GRADUAÇÃO - FIAP 2025 - SERVIÇO DE RECEBIMENTO DE PEDIDOS";
        assertEquals(tituloEsperado, openAPI.getInfo().getTitle());
    }

    @Test
    void deveConfigurarVersaoCorretamente() {
        String versaoEsperada = "1.0.0";
        assertEquals(versaoEsperada, openAPI.getInfo().getVersion());
    }

    @Test
    void deveConfigurarDescricaoCorretamente() {
        String descricaoEsperada = "Microsserviço responsável por receber solicitações de novos pedidos, validar dados iniciais e encaminhar as requisições para o serviço de pedidos. Atua como ponto de entrada para integrações externas e garante a correta orquestração do fluxo de pedidos no sistema.";
        assertEquals(descricaoEsperada, openAPI.getInfo().getDescription());
    }

    @Test
    void deveRetornarNovaInstanciaACadaChamada() {
        OpenAPI primeiraInstancia = openApiConfig.customOpenAPI();
        OpenAPI segundaInstancia = openApiConfig.customOpenAPI();

        assertNotSame(primeiraInstancia, segundaInstancia);
    }

    @Test
    void deveConfigurarTodasAsInformacoesObrigatorias() {
        Info info = openAPI.getInfo();

        assertAll(
                () -> assertNotNull(info.getTitle()),
                () -> assertNotNull(info.getVersion()),
                () -> assertNotNull(info.getDescription()),
                () -> assertFalse(info.getTitle().isEmpty()),
                () -> assertFalse(info.getVersion().isEmpty()),
                () -> assertFalse(info.getDescription().isEmpty())
        );
    }
}