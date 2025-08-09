package com.fiap.receiver.mapper;

import com.fiap.receiver.domain.Pagamento;
import com.fiap.receiver.domain.PedidoReceiver;
import com.fiap.receiver.dto.request.ItemPedidoRequestDTO;
import com.fiap.receiver.dto.request.PedidoRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PedidoKafkaMapperTest {

    @InjectMocks
    private PedidoKafkaMapper pedidoKafkaMapper;

    private PedidoReceiver pedidoReceiver;
    private PedidoReceiver.Item item1;
    private PedidoReceiver.Item item2;
    private Pagamento pagamento;

    @BeforeEach
    void setUp() {
        item1 = new PedidoReceiver.Item();
        item1.setProdutoId("PROD-001");
        item1.setQuantidade(2);

        item2 = new PedidoReceiver.Item();
        item2.setProdutoId("PROD-002");
        item2.setQuantidade(1);

        pagamento = new Pagamento();
        pagamento.setNumeroCartao("1234567890123456");

        pedidoReceiver = new PedidoReceiver();
        pedidoReceiver.setClienteId("12345");
        pedidoReceiver.setItens(Arrays.asList(item1, item2));
        pedidoReceiver.setPagamento(pagamento);
    }

    @Test
    void deveConverterPedidoReceiverParaPedidoRequestDTOComSucesso() {
        PedidoRequestDTO resultado = pedidoKafkaMapper.toPedidoRequestDTO(pedidoReceiver);

        assertNotNull(resultado);
        assertEquals(12345L, resultado.getClienteId());
        assertNotNull(resultado.getItens());
        assertEquals(2, resultado.getItens().size());

        ItemPedidoRequestDTO itemResult1 = resultado.getItens().get(0);
        assertEquals("PROD-001", itemResult1.getProdutoId());
        assertEquals(2, itemResult1.getQuantidade());

        ItemPedidoRequestDTO itemResult2 = resultado.getItens().get(1);
        assertEquals("PROD-002", itemResult2.getProdutoId());
        assertEquals(1, itemResult2.getQuantidade());

        assertNotNull(resultado.getDadosPagamento());
        assertEquals("1234567890123456", resultado.getDadosPagamento().getNumeroCartao());
        assertEquals("CARTAO", resultado.getDadosPagamento().getMetodoPagamento());
    }

    @Test
    void deveConverterComClienteIdNulo() {
        pedidoReceiver.setClienteId(null);

        PedidoRequestDTO resultado = pedidoKafkaMapper.toPedidoRequestDTO(pedidoReceiver);

        assertNotNull(resultado);
        assertNull(resultado.getClienteId());
        assertNotNull(resultado.getItens());
        assertNotNull(resultado.getDadosPagamento());
    }

    @Test
    void deveConverterComClienteIdVazio() {
        pedidoReceiver.setClienteId("");

        assertThrows(NumberFormatException.class, () -> {
            pedidoKafkaMapper.toPedidoRequestDTO(pedidoReceiver);
        });
    }

    @Test
    void deveConverterComItensNulos() {
        pedidoReceiver.setItens(null);

        PedidoRequestDTO resultado = pedidoKafkaMapper.toPedidoRequestDTO(pedidoReceiver);

        assertNotNull(resultado);
        assertEquals(12345L, resultado.getClienteId());
        assertNull(resultado.getItens());
        assertNotNull(resultado.getDadosPagamento());
    }

    @Test
    void deveConverterComListaItensVazia() {
        pedidoReceiver.setItens(Collections.emptyList());

        PedidoRequestDTO resultado = pedidoKafkaMapper.toPedidoRequestDTO(pedidoReceiver);

        assertNotNull(resultado);
        assertEquals(12345L, resultado.getClienteId());
        assertNotNull(resultado.getItens());
        assertTrue(resultado.getItens().isEmpty());
        assertNotNull(resultado.getDadosPagamento());
    }

    @Test
    void deveConverterComPagamentoNulo() {
        pedidoReceiver.setPagamento(null);

        PedidoRequestDTO resultado = pedidoKafkaMapper.toPedidoRequestDTO(pedidoReceiver);

        assertNotNull(resultado);
        assertEquals(12345L, resultado.getClienteId());
        assertNotNull(resultado.getItens());
        assertEquals(2, resultado.getItens().size());
        assertNull(resultado.getDadosPagamento());
    }

    @Test
    void deveConverterComPedidoReceiverNulo() {
        assertThrows(NullPointerException.class, () -> {
            pedidoKafkaMapper.toPedidoRequestDTO(null);
        });
    }

    @Test
    void deveConverterComItemComProdutoIdNulo() {
        item1.setProdutoId(null);

        PedidoRequestDTO resultado = pedidoKafkaMapper.toPedidoRequestDTO(pedidoReceiver);

        assertNotNull(resultado);
        assertNotNull(resultado.getItens());
        assertEquals(2, resultado.getItens().size());

        ItemPedidoRequestDTO itemResult1 = resultado.getItens().get(0);
        assertNull(itemResult1.getProdutoId());
        assertEquals(2, itemResult1.getQuantidade());
    }

    @Test
    void deveConverterComItemComQuantidadeZero() {
        item1.setQuantidade(0);

        PedidoRequestDTO resultado = pedidoKafkaMapper.toPedidoRequestDTO(pedidoReceiver);

        assertNotNull(resultado);
        assertNotNull(resultado.getItens());
        assertEquals(2, resultado.getItens().size());

        ItemPedidoRequestDTO itemResult1 = resultado.getItens().get(0);
        assertEquals("PROD-001", itemResult1.getProdutoId());
        assertEquals(0, itemResult1.getQuantidade());
    }

    @Test
    void deveConverterComNumeroCartaoNulo() {
        pagamento.setNumeroCartao(null);

        PedidoRequestDTO resultado = pedidoKafkaMapper.toPedidoRequestDTO(pedidoReceiver);

        assertNotNull(resultado);
        assertNotNull(resultado.getDadosPagamento());
        assertNull(resultado.getDadosPagamento().getNumeroCartao());
        assertEquals("CARTAO", resultado.getDadosPagamento().getMetodoPagamento());
    }

    @Test
    void deveDefinirMetodoPagamentoComoCartaoSempre() {
        PedidoRequestDTO resultado = pedidoKafkaMapper.toPedidoRequestDTO(pedidoReceiver);

        assertNotNull(resultado);
        assertNotNull(resultado.getDadosPagamento());
        assertEquals("CARTAO", resultado.getDadosPagamento().getMetodoPagamento());
    }

    @Test
    void deveConverterClienteIdNumericoValido() {
        pedidoReceiver.setClienteId("999999");

        PedidoRequestDTO resultado = pedidoKafkaMapper.toPedidoRequestDTO(pedidoReceiver);

        assertNotNull(resultado);
        assertEquals(999999L, resultado.getClienteId());
    }

    @Test
    void deveConverterComUmItemApenas() {
        pedidoReceiver.setItens(List.of(item1));

        PedidoRequestDTO resultado = pedidoKafkaMapper.toPedidoRequestDTO(pedidoReceiver);

        assertNotNull(resultado);
        assertNotNull(resultado.getItens());
        assertEquals(1, resultado.getItens().size());
        assertEquals("PROD-001", resultado.getItens().get(0).getProdutoId());
        assertEquals(2, resultado.getItens().get(0).getQuantidade());
    }
}