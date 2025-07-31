package com.fiap.receiver.gateway.impl;

import com.fiap.receiver.domain.PedidoReceiver;
import com.fiap.receiver.dto.request.DadosPagamentoRequestDTO;
import com.fiap.receiver.dto.request.ItemPedidoRequestDTO;
import com.fiap.receiver.dto.request.PedidoRequestDTO;
import com.fiap.receiver.mapper.PedidoKafkaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoReceiverServiceKafkaGatewayTest {

    @Mock
    private KafkaTemplate<String, PedidoRequestDTO> kafkaTemplate;

    @Mock
    private PedidoKafkaMapper pedidoKafkaMapper;

    @InjectMocks
    private PedidoReceiverServiceKafkaGateway pedidoReceiverServiceKafkaGateway;

    private PedidoReceiver pedidoReceiver;
    private PedidoRequestDTO pedidoRequestDTO;
    private final String TOPICO_NOVO_PEDIDO = "novo-pedido";

    @BeforeEach
    void configurarObjetos() {
        List<PedidoReceiver.Item> itens = Arrays.asList(
                new PedidoReceiver.Item() {{
                    setProdutoId("PROD001");
                    setQuantidade(2);
                }},
                new PedidoReceiver.Item() {{
                    setProdutoId("PROD002");
                    setQuantidade(1);
                }}
        );

        pedidoReceiver = new PedidoReceiver();
        pedidoReceiver.setClienteId("123");
        pedidoReceiver.setItens(itens);

        List<ItemPedidoRequestDTO> itensDTO = Arrays.asList(
                ItemPedidoRequestDTO.builder()
                        .produtoId("PROD001")
                        .quantidade(2)
                        .build(),
                ItemPedidoRequestDTO.builder()
                        .produtoId("PROD002")
                        .quantidade(1)
                        .build()
        );

        pedidoRequestDTO = PedidoRequestDTO.builder()
                .id(1L)
                .clienteId(123L)
                .itens(itensDTO)
                .status("PENDENTE")
                .dataCriacao(LocalDateTime.now())
                .dadosPagamento(DadosPagamentoRequestDTO.builder().build())
                .build();
    }

    @Test
    void deveEncaminharPedidoComSucesso() {
        when(pedidoKafkaMapper.toPedidoRequestDTO(pedidoReceiver)).thenReturn(pedidoRequestDTO);

        pedidoReceiverServiceKafkaGateway.encaminharPedido(pedidoReceiver);

        verify(pedidoKafkaMapper).toPedidoRequestDTO(pedidoReceiver);
        verify(kafkaTemplate).send(eq(TOPICO_NOVO_PEDIDO), eq("123"), eq(pedidoRequestDTO));
    }

    @Test
    void deveUtilizarClienteIdComoChaveNaFila() {
        when(pedidoKafkaMapper.toPedidoRequestDTO(pedidoReceiver)).thenReturn(pedidoRequestDTO);

        pedidoReceiverServiceKafkaGateway.encaminharPedido(pedidoReceiver);

        verify(kafkaTemplate).send(TOPICO_NOVO_PEDIDO, "123", pedidoRequestDTO);
    }

    @Test
    void deveEncaminharPedidoComClienteIdDiferente() {
        PedidoRequestDTO pedidoComClienteIdDiferente = PedidoRequestDTO.builder()
                .id(2L)
                .clienteId(456L)
                .status("CONFIRMADO")
                .build();

        when(pedidoKafkaMapper.toPedidoRequestDTO(pedidoReceiver)).thenReturn(pedidoComClienteIdDiferente);

        pedidoReceiverServiceKafkaGateway.encaminharPedido(pedidoReceiver);

        verify(kafkaTemplate).send(TOPICO_NOVO_PEDIDO, "456", pedidoComClienteIdDiferente);
    }

    @Test
    void deveInvocarMapperAntesDeEnviarParaKafka() {
        when(pedidoKafkaMapper.toPedidoRequestDTO(pedidoReceiver)).thenReturn(pedidoRequestDTO);

        pedidoReceiverServiceKafkaGateway.encaminharPedido(pedidoReceiver);

        verify(pedidoKafkaMapper).toPedidoRequestDTO(pedidoReceiver);
        verify(kafkaTemplate).send(any(), any(), any());
    }

    @Test
    void deveUtilizarTopicoCorreto() {
        when(pedidoKafkaMapper.toPedidoRequestDTO(pedidoReceiver)).thenReturn(pedidoRequestDTO);

        pedidoReceiverServiceKafkaGateway.encaminharPedido(pedidoReceiver);

        verify(kafkaTemplate).send(eq("novo-pedido"), any(), any());
    }

    @Test
    void devePassarPedidoReceiverParaMapper() {
        when(pedidoKafkaMapper.toPedidoRequestDTO(pedidoReceiver)).thenReturn(pedidoRequestDTO);

        pedidoReceiverServiceKafkaGateway.encaminharPedido(pedidoReceiver);

        verify(pedidoKafkaMapper).toPedidoRequestDTO(eq(pedidoReceiver));
    }

    @Test
    void deveEnviarObjetoConvertidoParaKafka() {
        when(pedidoKafkaMapper.toPedidoRequestDTO(pedidoReceiver)).thenReturn(pedidoRequestDTO);

        pedidoReceiverServiceKafkaGateway.encaminharPedido(pedidoReceiver);

        verify(kafkaTemplate).send(any(), any(), eq(pedidoRequestDTO));
    }

    @Test
    void deveEncaminharPedidoComClienteIdNulo() {
        PedidoRequestDTO pedidoComClienteIdNulo = PedidoRequestDTO.builder()
                .id(1L)
                .clienteId(null)
                .status("PENDENTE")
                .build();

        pedidoReceiver.setClienteId(null);
        when(pedidoKafkaMapper.toPedidoRequestDTO(pedidoReceiver)).thenReturn(pedidoComClienteIdNulo);

        pedidoReceiverServiceKafkaGateway.encaminharPedido(pedidoReceiver);

        verify(kafkaTemplate).send(TOPICO_NOVO_PEDIDO, "null", pedidoComClienteIdNulo);
    }
}