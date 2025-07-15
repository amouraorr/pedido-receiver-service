package com.fiap.receiver.controller;

import com.fiap.receiver.dto.request.PedidoRequestDTO;
import com.fiap.receiver.mapper.PedidoMapper;
import com.fiap.receiver.usecase.service.ReceberPedidoServiceUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final ReceberPedidoServiceUseCase receberPedidoUseCase;
    private final PedidoMapper pedidoMapper;

    public PedidoController(ReceberPedidoServiceUseCase receberPedidoUseCase, PedidoMapper pedidoMapper) {
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
