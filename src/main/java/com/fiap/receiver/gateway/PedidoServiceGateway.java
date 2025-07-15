package com.fiap.receiver.gateway;

import com.fiap.receiver.domain.Pedido;

public interface PedidoServiceGateway {
    void encaminharPedido(Pedido pedido);
}
