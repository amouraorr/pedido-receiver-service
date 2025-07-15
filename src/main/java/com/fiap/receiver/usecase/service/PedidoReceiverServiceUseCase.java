package com.fiap.receiver.usecase.service;

import com.fiap.receiver.domain.PedidoReceiver;
import com.fiap.receiver.gateway.PedidoReceiverServiceGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PedidoReceiverServiceUseCase {

    private final PedidoReceiverServiceGateway pedidoServiceGateway;

    public PedidoReceiverServiceUseCase(PedidoReceiverServiceGateway pedidoServiceGateway) {
        this.pedidoServiceGateway = pedidoServiceGateway;
    }

    public void processar(PedidoReceiver pedido) {
        log.debug("Validando pedido para clienteId={}", pedido.getClienteId());
        if (pedido.getClienteId() == null || pedido.getItens() == null || pedido.getItens().isEmpty()) {
            log.error("Pedido inválido: clienteId ou itens ausentes");
            throw new IllegalArgumentException("Pedido inválido: clienteId e itens são obrigatórios");
        }
        log.debug("Encaminhando pedido para Kafka para clienteId={}", pedido.getClienteId());
        pedidoServiceGateway.encaminharPedido(pedido);
        log.debug("Pedido encaminhado com sucesso para clienteId={}", pedido.getClienteId());
    }
}