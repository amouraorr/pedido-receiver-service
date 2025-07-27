package com.fiap.receiver.mapper;

import com.fiap.receiver.domain.PedidoReceiver;
import com.fiap.receiver.dto.request.DadosPagamentoRequestDTO;
import com.fiap.receiver.dto.request.ItemPedidoRequestDTO;
import com.fiap.receiver.dto.request.PedidoRequestDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PedidoKafkaMapper {

    public PedidoRequestDTO toPedidoRequestDTO(PedidoReceiver pedidoReceiver) {
        return PedidoRequestDTO.builder()
                .clienteId(pedidoReceiver.getClienteId() != null ? Long.valueOf(pedidoReceiver.getClienteId()) : null)
                .itens(
                        pedidoReceiver.getItens() != null ?
                                pedidoReceiver.getItens().stream()
                                        .map(item -> ItemPedidoRequestDTO.builder()
                                                .produtoId(item.getProdutoId())
                                                .quantidade(item.getQuantidade())
                                                .build())
                                        .collect(Collectors.toList())
                                : null
                )
                .dadosPagamento(
                        pedidoReceiver.getDadosPagamento() != null ?
                                DadosPagamentoRequestDTO.builder()
                                        .numeroCartao(pedidoReceiver.getDadosPagamento().getNumeroCartao())
                                        .metodoPagamento("CARTAO") // ou outro valor default
                                        .build()
                                : null
                )
                .build();
    }
}