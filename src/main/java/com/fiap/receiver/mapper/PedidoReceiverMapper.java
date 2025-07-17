package com.fiap.receiver.mapper;

import com.fiap.receiver.domain.PedidoReceiver;
import com.fiap.receiver.domain.Pagamento;
import com.fiap.receiver.dto.request.PedidoReceiverRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PedidoReceiverMapper {
    PedidoReceiverMapper INSTANCE = Mappers.getMapper(PedidoReceiverMapper.class);

    @Mapping(source = "dadosPagamento", target = "dadosPagamento")
    PedidoReceiver toPedido(PedidoReceiverRequestDTO dto);

    Pagamento toPagamento(PedidoReceiverRequestDTO.PagamentoDTO dto);
}