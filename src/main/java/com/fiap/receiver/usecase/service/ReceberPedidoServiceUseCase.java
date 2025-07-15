package com.fiap.receiver.usecase.service;

import com.fiap.receiver.domain.Pedido;
import com.fiap.receiver.gateway.PedidoServiceGateway;
import org.springframework.stereotype.Service;

@Service
public class ReceberPedidoServiceUseCase {

    private final PedidoServiceGateway pedidoServiceGateway;

    public ReceberPedidoServiceUseCase(PedidoServiceGateway pedidoServiceGateway) {
        this.pedidoServiceGateway = pedidoServiceGateway;
    }

    public void processar(Pedido pedido) {
        // Validação inicial dos dados do pedido (exemplo simplificado)
        if (pedido.getClienteId() == null || pedido.getItens() == null || pedido.getItens().isEmpty()) {
            throw new IllegalArgumentException("Pedido inválido");
        }
        pedidoServiceGateway.encaminharPedido(pedido);
    }
}