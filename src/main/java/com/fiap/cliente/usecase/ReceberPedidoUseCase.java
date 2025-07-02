package com.fiap.cliente.usecase;

import com.fiap.cliente.domain.Pedido;
import com.fiap.cliente.gateway.PedidoServiceGateway;
import org.springframework.stereotype.Service;

@Service
public class ReceberPedidoUseCase {

    private final PedidoServiceGateway pedidoServiceGateway;

    public ReceberPedidoUseCase(PedidoServiceGateway pedidoServiceGateway) {
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