package com.fiap.receiver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoReceiverServiceApplicationTest {

    @Test
    void deveVerificarSeClassePossuiAnotacaoSpringBootApplication() {
        boolean possuiAnotacao = PedidoReceiverServiceApplication.class.isAnnotationPresent(SpringBootApplication.class);

        assertTrue(possuiAnotacao);
    }

    @Test
    void deveVerificarSeMetodoMainExiste() {
        assertDoesNotThrow(() -> {
            PedidoReceiverServiceApplication.class.getDeclaredMethod("main", String[].class);
        });
    }

    @Test
    void deveExecutarMetodoMainSemExcecao() {
        String[] argumentos = {"--spring.main.web-application-type=none", "--spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"};

        try (MockedStatic<SpringApplication> mockSpringApplication = mockStatic(SpringApplication.class)) {
            ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);
            mockSpringApplication.when(() -> SpringApplication.run(PedidoReceiverServiceApplication.class, argumentos))
                    .thenReturn(mockContext);

            assertDoesNotThrow(() -> PedidoReceiverServiceApplication.main(argumentos));

            mockSpringApplication.verify(() -> SpringApplication.run(PedidoReceiverServiceApplication.class, argumentos), times(1));
        }
    }

    @Test
    void deveExecutarMetodoMainComArgumentosVazios() {
        String[] argumentosVazios = {};

        try (MockedStatic<SpringApplication> mockSpringApplication = mockStatic(SpringApplication.class)) {
            ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);
            mockSpringApplication.when(() -> SpringApplication.run(PedidoReceiverServiceApplication.class, argumentosVazios))
                    .thenReturn(mockContext);

            assertDoesNotThrow(() -> PedidoReceiverServiceApplication.main(argumentosVazios));

            mockSpringApplication.verify(() -> SpringApplication.run(PedidoReceiverServiceApplication.class, argumentosVazios), times(1));
        }
    }

    @Test
    void deveExecutarMetodoMainComArgumentosNulos() {
        String[] argumentosNulos = null;

        try (MockedStatic<SpringApplication> mockSpringApplication = mockStatic(SpringApplication.class)) {
            ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);
            mockSpringApplication.when(() -> SpringApplication.run(PedidoReceiverServiceApplication.class, argumentosNulos))
                    .thenReturn(mockContext);

            assertDoesNotThrow(() -> PedidoReceiverServiceApplication.main(argumentosNulos));

            mockSpringApplication.verify(() -> SpringApplication.run(PedidoReceiverServiceApplication.class, argumentosNulos), times(1));
        }
    }

    @Test
    void deveVerificarSeClasseEPublica() {
        int modificadores = PedidoReceiverServiceApplication.class.getModifiers();

        assertTrue(java.lang.reflect.Modifier.isPublic(modificadores));
    }

    @Test
    void deveVerificarSeClasseNaoEAbstrata() {
        int modificadores = PedidoReceiverServiceApplication.class.getModifiers();

        assertFalse(java.lang.reflect.Modifier.isAbstract(modificadores));
    }

    @Test
    void deveVerificarSeClasseNaoEFinal() {
        int modificadores = PedidoReceiverServiceApplication.class.getModifiers();

        assertFalse(java.lang.reflect.Modifier.isFinal(modificadores));
    }

    @Test
    void deveVerificarSeMetodoMainEEstatico() throws NoSuchMethodException {
        var metodoMain = PedidoReceiverServiceApplication.class.getDeclaredMethod("main", String[].class);
        int modificadores = metodoMain.getModifiers();

        assertTrue(java.lang.reflect.Modifier.isStatic(modificadores));
    }

    @Test
    void deveVerificarSeMetodoMainEPublico() throws NoSuchMethodException {
        var metodoMain = PedidoReceiverServiceApplication.class.getDeclaredMethod("main", String[].class);
        int modificadores = metodoMain.getModifiers();

        assertTrue(java.lang.reflect.Modifier.isPublic(modificadores));
    }

    @Test
    void deveVerificarSeMetodoMainRetornaVoid() throws NoSuchMethodException {
        var metodoMain = PedidoReceiverServiceApplication.class.getDeclaredMethod("main", String[].class);
        Class<?> tipoRetorno = metodoMain.getReturnType();

        assertEquals(void.class, tipoRetorno);
    }
}