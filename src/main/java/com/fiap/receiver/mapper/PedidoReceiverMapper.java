package com.fiap.receiver.mapper;

import com.fiap.receiver.domain.PedidoReceiver;
import com.fiap.receiver.dto.request.PedidoReceiverRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PedidoReceiverMapper {
    PedidoReceiverMapper INSTANCE = Mappers.getMapper(PedidoReceiverMapper.class);

    PedidoReceiver toPedido(PedidoReceiverRequestDTO dto);
}
