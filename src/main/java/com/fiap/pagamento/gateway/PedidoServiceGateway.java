package com.fiap.pagamento.gateway;

import com.fiap.pagamento.domain.Pedido;

public interface PedidoServiceGateway {
    void encaminharPedido(Pedido pedido);
}
