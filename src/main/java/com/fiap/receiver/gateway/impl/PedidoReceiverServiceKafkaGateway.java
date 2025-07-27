package com.fiap.receiver.gateway.impl;

import com.fiap.receiver.domain.PedidoReceiver;
import com.fiap.receiver.dto.request.PedidoRequestDTO;
import com.fiap.receiver.gateway.PedidoReceiverServiceGateway;
import com.fiap.receiver.mapper.PedidoKafkaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoReceiverServiceKafkaGateway implements PedidoReceiverServiceGateway {

    private final KafkaTemplate<String, PedidoRequestDTO> kafkaTemplate;
    private final PedidoKafkaMapper pedidoKafkaMapper;

    private static final String TOPICO_NOVO_PEDIDO = "novo-pedido";

    @Override
    public void encaminharPedido(PedidoReceiver pedido) {
        PedidoRequestDTO pedidoRequestDTO = pedidoKafkaMapper.toPedidoRequestDTO(pedido);
        kafkaTemplate.send(TOPICO_NOVO_PEDIDO, String.valueOf(pedidoRequestDTO.getClienteId()), pedidoRequestDTO);
    }
}