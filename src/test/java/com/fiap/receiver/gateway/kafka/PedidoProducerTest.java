package com.fiap.receiver.gateway.kafka;

import com.fiap.receiver.dto.request.PedidoReceiverRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoProducerTest {

    @Mock
    private KafkaTemplate<String, PedidoReceiverRequestDTO> kafkaTemplate;

    @Mock
    private CompletableFuture<SendResult<String, PedidoReceiverRequestDTO>> sendResult;

    @InjectMocks
    private PedidoProducer pedidoProducer;

    private PedidoReceiverRequestDTO pedidoRequestDTO;
    private String clienteId;

    @BeforeEach
    void setUp() {
        clienteId = "cliente-123";
        pedidoRequestDTO = criarPedidoRequestDTO();
    }

    @Test
    void deveEnviarPedidoComSucesso() {
        when(kafkaTemplate.send(anyString(), anyString(), any(PedidoReceiverRequestDTO.class)))
                .thenReturn(sendResult);

        pedidoProducer.enviarPedido(pedidoRequestDTO);

        verify(kafkaTemplate).send("novo-pedido", clienteId, pedidoRequestDTO);
    }

    @Test
    void deveEnviarPedidoComTopicoCorreto() {
        when(kafkaTemplate.send(anyString(), anyString(), any(PedidoReceiverRequestDTO.class)))
                .thenReturn(sendResult);

        pedidoProducer.enviarPedido(pedidoRequestDTO);

        verify(kafkaTemplate).send(eq("novo-pedido"), any(), any());
    }

    @Test
    void deveEnviarPedidoComChaveClienteId() {
        when(kafkaTemplate.send(anyString(), anyString(), any(PedidoReceiverRequestDTO.class)))
                .thenReturn(sendResult);

        pedidoProducer.enviarPedido(pedidoRequestDTO);

        verify(kafkaTemplate).send(any(), eq(clienteId), any());
    }

    @Test
    void deveEnviarPedidoComPayloadCorreto() {
        when(kafkaTemplate.send(anyString(), anyString(), any(PedidoReceiverRequestDTO.class)))
                .thenReturn(sendResult);

        pedidoProducer.enviarPedido(pedidoRequestDTO);

        verify(kafkaTemplate).send(any(), any(), eq(pedidoRequestDTO));
    }

    @Test
    void deveExecutarSendApenasUmaVez() {
        when(kafkaTemplate.send(anyString(), anyString(), any(PedidoReceiverRequestDTO.class)))
                .thenReturn(sendResult);

        pedidoProducer.enviarPedido(pedidoRequestDTO);

        verify(kafkaTemplate, times(1)).send(anyString(), anyString(), any(PedidoReceiverRequestDTO.class));
    }

    @Test
    void deveEnviarPedidoComClienteIdDiferente() {
        String outroClienteId = "cliente-456";
        pedidoRequestDTO.setClienteId(outroClienteId);

        when(kafkaTemplate.send(anyString(), anyString(), any(PedidoReceiverRequestDTO.class)))
                .thenReturn(sendResult);

        pedidoProducer.enviarPedido(pedidoRequestDTO);

        verify(kafkaTemplate).send("novo-pedido", outroClienteId, pedidoRequestDTO);
    }

    @Test
    void deveEnviarMultiplosPedidos() {
        PedidoReceiverRequestDTO segundoPedido = criarPedidoRequestDTO();
        segundoPedido.setClienteId("cliente-789");

        when(kafkaTemplate.send(anyString(), anyString(), any(PedidoReceiverRequestDTO.class)))
                .thenReturn(sendResult);

        pedidoProducer.enviarPedido(pedidoRequestDTO);
        pedidoProducer.enviarPedido(segundoPedido);

        verify(kafkaTemplate, times(2)).send(anyString(), anyString(), any(PedidoReceiverRequestDTO.class));
    }

    @Test
    void deveLancarExcecaoQuandoPedidoForNulo() {
        assertThrows(NullPointerException.class, () -> pedidoProducer.enviarPedido(null));
        verify(kafkaTemplate, never()).send(anyString(), anyString(), any(PedidoReceiverRequestDTO.class));
    }

    @Test
    void deveLancarExcecaoQuandoClienteIdForNulo() {
        pedidoRequestDTO.setClienteId(null);

        assertThrows(NullPointerException.class, () -> pedidoProducer.enviarPedido(pedidoRequestDTO));
        verify(kafkaTemplate, never()).send(anyString(), anyString(), any(PedidoReceiverRequestDTO.class));
    }

    @Test
    void deveRepassarExcecaoDoKafkaTemplate() {
        RuntimeException kafkaException = new RuntimeException("Erro no Kafka");
        when(kafkaTemplate.send(anyString(), anyString(), any(PedidoReceiverRequestDTO.class)))
                .thenThrow(kafkaException);

        assertThrows(RuntimeException.class, () -> pedidoProducer.enviarPedido(pedidoRequestDTO));
        verify(kafkaTemplate).send("novo-pedido", clienteId, pedidoRequestDTO);
    }

    @Test
    void deveValidarParametrosCompletos() {
        when(kafkaTemplate.send(anyString(), anyString(), any(PedidoReceiverRequestDTO.class)))
                .thenReturn(sendResult);

        pedidoProducer.enviarPedido(pedidoRequestDTO);

        verify(kafkaTemplate).send(
                eq("novo-pedido"),
                eq(clienteId),
                eq(pedidoRequestDTO)
        );
    }

    @Test
    void naoDeveModificarPedidoAntesDoEnvio() {
        PedidoReceiverRequestDTO pedidoOriginal = criarPedidoRequestDTO();
        String clienteIdOriginal = pedidoOriginal.getClienteId();

        when(kafkaTemplate.send(anyString(), anyString(), any(PedidoReceiverRequestDTO.class)))
                .thenReturn(sendResult);

        pedidoProducer.enviarPedido(pedidoOriginal);

        assertEquals(clienteIdOriginal, pedidoOriginal.getClienteId());
    }

    @Test
    void deveEnviarPedidoComItensCompletos() {
        when(kafkaTemplate.send(anyString(), anyString(), any(PedidoReceiverRequestDTO.class)))
                .thenReturn(sendResult);

        pedidoProducer.enviarPedido(pedidoRequestDTO);

        verify(kafkaTemplate).send(eq("novo-pedido"), eq(clienteId), argThat(pedido ->
                pedido.getItens() != null && !pedido.getItens().isEmpty()
        ));
    }

    @Test
    void deveEnviarPedidoComDadosPagamento() {
        when(kafkaTemplate.send(anyString(), anyString(), any(PedidoReceiverRequestDTO.class)))
                .thenReturn(sendResult);

        pedidoProducer.enviarPedido(pedidoRequestDTO);

        verify(kafkaTemplate).send(eq("novo-pedido"), eq(clienteId), argThat(pedido ->
                pedido.getDadosPagamento() != null
        ));
    }

    private PedidoReceiverRequestDTO criarPedidoRequestDTO() {
        PedidoReceiverRequestDTO dto = new PedidoReceiverRequestDTO();
        dto.setClienteId(clienteId);

        PedidoReceiverRequestDTO.ItemDTO item1 = new PedidoReceiverRequestDTO.ItemDTO();
        item1.setProdutoId("produto-123");
        item1.setQuantidade(2);

        PedidoReceiverRequestDTO.ItemDTO item2 = new PedidoReceiverRequestDTO.ItemDTO();
        item2.setProdutoId("produto-456");
        item2.setQuantidade(1);

        dto.setItens(Arrays.asList(item1, item2));

        PedidoReceiverRequestDTO.PagamentoDTO pagamento = new PedidoReceiverRequestDTO.PagamentoDTO();
        pagamento.setNumeroCartao("1234567890123456");
        dto.setDadosPagamento(pagamento);

        return dto;
    }
}