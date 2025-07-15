package com.fiap.receiver.gateway.impl;

import com.fiap.receiver.domain.Pedido;
import com.fiap.receiver.gateway.PedidoServiceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

// Implementação do gateway para enviar pedidos ao tópico Kafka
@Component
@RequiredArgsConstructor
public class PedidoServiceKafkaGateway implements PedidoServiceGateway {

    private final KafkaTemplate<String, Pedido> kafkaTemplate;

    // Nome do tópico Kafka (ajuste conforme necessário)
    private static final String TOPICO_NOVO_PEDIDO = "novo-pedido-queue";

    @Override
    public void encaminharPedido(Pedido pedido) {
        // Envia o pedido serializado para o tópico Kafka
        kafkaTemplate.send(TOPICO_NOVO_PEDIDO, pedido.getClienteId(), pedido);

    }
}