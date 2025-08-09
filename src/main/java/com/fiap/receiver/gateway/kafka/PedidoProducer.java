package com.fiap.receiver.gateway.kafka;

import com.fiap.receiver.dto.request.PedidoReceiverRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PedidoProducer {

    private final KafkaTemplate<String, PedidoReceiverRequestDTO> kafkaTemplate;
    private static final String TOPICO_NOVO_PEDIDO = "novo-pedido";

    public void enviarPedido(PedidoReceiverRequestDTO pedido) {
        kafkaTemplate.send(TOPICO_NOVO_PEDIDO, pedido.getClienteId().toString(), pedido);
        log.info("Pedido enviado para Kafka: {}", pedido);
    }
}