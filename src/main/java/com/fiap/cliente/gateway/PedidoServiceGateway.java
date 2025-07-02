package com.fiap.cliente.gateway;

import com.fiap.cliente.domain.Pedido;

public interface PedidoServiceGateway {
    void encaminharPedido(Pedido pedido);
}
