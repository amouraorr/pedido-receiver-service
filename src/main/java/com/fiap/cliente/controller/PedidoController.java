package com.fiap.cliente.controller;

import com.fiap.cliente.dto.PedidoRequestDTO;
import com.fiap.cliente.mapper.PedidoMapper;
import com.fiap.cliente.usecase.ReceberPedidoUseCase;
import lombok.var;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final ReceberPedidoUseCase receberPedidoUseCase;
    private final PedidoMapper pedidoMapper;

    public PedidoController(ReceberPedidoUseCase receberPedidoUseCase, PedidoMapper pedidoMapper) {
        this.receberPedidoUseCase = receberPedidoUseCase;
        this.pedidoMapper = pedidoMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void receberPedido(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        var pedido = pedidoMapper.toPedido(pedidoRequestDTO);
        receberPedidoUseCase.processar(pedido);
    }
}
