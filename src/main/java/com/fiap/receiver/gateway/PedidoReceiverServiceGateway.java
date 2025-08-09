package com.fiap.receiver.gateway;

import com.fiap.receiver.domain.PedidoReceiver;

public interface PedidoReceiverServiceGateway {
    void encaminharPedido(PedidoReceiver pedido);
}
