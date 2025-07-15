package com.fiap.receiver.mapper;

import com.fiap.receiver.domain.Pedido;
import com.fiap.receiver.dto.request.PedidoRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PedidoMapper {
    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

    Pedido toPedido(PedidoRequestDTO dto);
}
