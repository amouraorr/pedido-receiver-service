package com.fiap.receiver.usecase.service;

import com.fiap.receiver.domain.Pagamento;
import com.fiap.receiver.domain.PedidoReceiver;
import com.fiap.receiver.gateway.PedidoReceiverServiceGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoReceiverServiceUseCaseTest {

    @Mock
    private PedidoReceiverServiceGateway pedidoServiceGateway;

    @InjectMocks
    private PedidoReceiverServiceUseCase pedidoReceiverServiceUseCase;

    private PedidoReceiver pedidoValido;
    private PedidoReceiver pedidoInvalido;

    @BeforeEach
    void setUp() {
        pedidoValido = criarPedidoValido();
        pedidoInvalido = new PedidoReceiver();
    }

    private PedidoReceiver criarPedidoValido() {
        PedidoReceiver pedido = new PedidoReceiver();
        pedido.setClienteId("123");

        PedidoReceiver.Item item = new PedidoReceiver.Item();
        item.setProdutoId("PROD001");
        item.setQuantidade(2);

        pedido.setItens(List.of(item));

        Pagamento pagamento = new Pagamento();
        pagamento.setNumeroCartao("1234567890123456");
        pedido.setPagamento(pagamento);

        return pedido;
    }

    @Test
    void deveInstanciarUseCaseCorretamente() {
        assertNotNull(pedidoReceiverServiceUseCase);
    }

    @Test
    void deveProcessarPedidoValidoComSucesso() {
        doNothing().when(pedidoServiceGateway).encaminharPedido(pedidoValido);

        assertDoesNotThrow(() -> pedidoReceiverServiceUseCase.processar(pedidoValido));

        verify(pedidoServiceGateway, times(1)).encaminharPedido(pedidoValido);
    }

    @Test
    void deveLancarExcecaoQuandoClienteIdForNulo() {
        pedidoValido.setClienteId(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pedidoReceiverServiceUseCase.processar(pedidoValido)
        );

        assertEquals("Pedido inválido: clienteId e itens são obrigatórios", exception.getMessage());
        verify(pedidoServiceGateway, never()).encaminharPedido(any());
    }

    @Test
    void deveLancarExcecaoQuandoItensForNulo() {
        pedidoValido.setItens(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pedidoReceiverServiceUseCase.processar(pedidoValido)
        );

        assertEquals("Pedido inválido: clienteId e itens são obrigatórios", exception.getMessage());
        verify(pedidoServiceGateway, never()).encaminharPedido(any());
    }

    @Test
    void deveLancarExcecaoQuandoItensForVazio() {
        pedidoValido.setItens(Collections.emptyList());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pedidoReceiverServiceUseCase.processar(pedidoValido)
        );

        assertEquals("Pedido inválido: clienteId e itens são obrigatórios", exception.getMessage());
        verify(pedidoServiceGateway, never()).encaminharPedido(any());
    }

    @Test
    void deveLancarExcecaoQuandoClienteIdEItensForemNulos() {
        pedidoInvalido.setClienteId(null);
        pedidoInvalido.setItens(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pedidoReceiverServiceUseCase.processar(pedidoInvalido)
        );

        assertEquals("Pedido inválido: clienteId e itens são obrigatórios", exception.getMessage());
        verify(pedidoServiceGateway, never()).encaminharPedido(any());
    }

    @Test
    void deveProcessarPedidoComMultiplosItens() {
        PedidoReceiver.Item item1 = new PedidoReceiver.Item();
        item1.setProdutoId("PROD001");
        item1.setQuantidade(2);

        PedidoReceiver.Item item2 = new PedidoReceiver.Item();
        item2.setProdutoId("PROD002");
        item2.setQuantidade(1);

        pedidoValido.setItens(Arrays.asList(item1, item2));
        doNothing().when(pedidoServiceGateway).encaminharPedido(pedidoValido);

        assertDoesNotThrow(() -> pedidoReceiverServiceUseCase.processar(pedidoValido));

        verify(pedidoServiceGateway, times(1)).encaminharPedido(pedidoValido);
    }

    @Test
    void deveProcessarPedidoComClienteIdValidoEItensComUmItem() {
        doNothing().when(pedidoServiceGateway).encaminharPedido(pedidoValido);

        pedidoReceiverServiceUseCase.processar(pedidoValido);

        verify(pedidoServiceGateway).encaminharPedido(pedidoValido);
        verifyNoMoreInteractions(pedidoServiceGateway);
    }

    @Test
    void deveValidarConstrutor() {
        PedidoReceiverServiceUseCase useCase = new PedidoReceiverServiceUseCase(pedidoServiceGateway);
        assertNotNull(useCase);
    }

    @Test
    void naoDeveProcessarQuandoClienteIdForStringVazia() {
        pedidoValido.setClienteId("");

        assertDoesNotThrow(() -> pedidoReceiverServiceUseCase.processar(pedidoValido));
        verify(pedidoServiceGateway, times(1)).encaminharPedido(pedidoValido);
    }

    @Test
    void deveProcessarPedidoComItensComQuantidadeZero() {
        PedidoReceiver.Item item = new PedidoReceiver.Item();
        item.setProdutoId("PROD001");
        item.setQuantidade(0);

        pedidoValido.setItens(List.of(item));
        doNothing().when(pedidoServiceGateway).encaminharPedido(pedidoValido);

        assertDoesNotThrow(() -> pedidoReceiverServiceUseCase.processar(pedidoValido));

        verify(pedidoServiceGateway, times(1)).encaminharPedido(pedidoValido);
    }
}