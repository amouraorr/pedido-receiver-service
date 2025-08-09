package com.fiap.receiver.exception;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    private ListAppender<ILoggingEvent> listAppender;
    private Logger logger;
    private final LocalDateTime fixedDateTime = LocalDateTime.of(2024, 1, 15, 10, 30, 0);

    @BeforeEach
    void configurarLogger() {
        logger = (Logger) LoggerFactory.getLogger(GlobalExceptionHandler.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    void deveRetornarBadRequestParaIllegalArgumentException() {
        String mensagemErro = "Argumento inválido fornecido";
        IllegalArgumentException exception = new IllegalArgumentException(mensagemErro);

        try (MockedStatic<LocalDateTime> mockedDateTime = mockStatic(LocalDateTime.class)) {
            mockedDateTime.when(LocalDateTime::now).thenReturn(fixedDateTime);

            ResponseEntity<Map<String, Object>> response = globalExceptionHandler
                    .handleIllegalArgumentException(exception);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());

            Map<String, Object> body = response.getBody();
            assertEquals(fixedDateTime, body.get("timestamp"));
            assertEquals(400, body.get("status"));
            assertEquals("Bad Request", body.get("error"));
            assertEquals(mensagemErro, body.get("message"));
        }
    }

    @Test
    void deveLogarErroParaIllegalArgumentException() {
        String mensagemErro = "Erro de validação";
        IllegalArgumentException exception = new IllegalArgumentException(mensagemErro);

        globalExceptionHandler.handleIllegalArgumentException(exception);

        assertEquals(1, listAppender.list.size());
        assertTrue(listAppender.list.get(0).getFormattedMessage()
                .contains("Erro de argumento inválido: " + mensagemErro));
    }

    @Test
    void deveRetornarInternalServerErrorParaExceptionGenerica() {
        Exception exception = new RuntimeException("Erro inesperado");

        try (MockedStatic<LocalDateTime> mockedDateTime = mockStatic(LocalDateTime.class)) {
            mockedDateTime.when(LocalDateTime::now).thenReturn(fixedDateTime);

            ResponseEntity<Map<String, Object>> response = globalExceptionHandler
                    .handleAllExceptions(exception);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertNotNull(response.getBody());

            Map<String, Object> body = response.getBody();
            assertEquals(fixedDateTime, body.get("timestamp"));
            assertEquals(500, body.get("status"));
            assertEquals("Internal Server Error", body.get("error"));
            assertEquals("Ocorreu um erro inesperado. Tente novamente mais tarde.", body.get("message"));
        }
    }

    @Test
    void deveLogarErroParaExceptionGenerica() {
        Exception exception = new RuntimeException("Erro inesperado do sistema");

        globalExceptionHandler.handleAllExceptions(exception);

        assertEquals(1, listAppender.list.size());
        assertTrue(listAppender.list.get(0).getFormattedMessage()
                .contains("Erro inesperado: "));
    }

    @Test
    void deveRetornarResponseEntityComBodyNaoNuloParaIllegalArgument() {
        IllegalArgumentException exception = new IllegalArgumentException("Teste");

        ResponseEntity<Map<String, Object>> response = globalExceptionHandler
                .handleIllegalArgumentException(exception);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(4, response.getBody().size());
    }

    @Test
    void deveRetornarResponseEntityComBodyNaoNuloParaExceptionGenerica() {
        Exception exception = new Exception("Teste genérico");

        ResponseEntity<Map<String, Object>> response = globalExceptionHandler
                .handleAllExceptions(exception);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(4, response.getBody().size());
    }

    @Test
    void deveManterMensagemOriginalDaIllegalArgumentException() {
        String mensagemOriginal = "Parâmetro X não pode ser nulo";
        IllegalArgumentException exception = new IllegalArgumentException(mensagemOriginal);

        ResponseEntity<Map<String, Object>> response = globalExceptionHandler
                .handleIllegalArgumentException(exception);

        assertEquals(mensagemOriginal, response.getBody().get("message"));
    }

    @Test
    void deveUsarMensagemPadraoParaExceptionGenerica() {
        Exception exception = new Exception("Qualquer erro");

        ResponseEntity<Map<String, Object>> response = globalExceptionHandler
                .handleAllExceptions(exception);

        assertEquals("Ocorreu um erro inesperado. Tente novamente mais tarde.",
                response.getBody().get("message"));
    }
}