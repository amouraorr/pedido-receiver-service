package com.fiap.receiver.controller;

import com.fiap.receiver.dto.request.PedidoReceiverRequestDTO;
import com.fiap.receiver.mapper.PedidoReceiverMapper;
import com.fiap.receiver.usecase.service.PedidoReceiverServiceUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/pedidos")
public class PedidoReceiverController {

    private final PedidoReceiverServiceUseCase receberPedidoUseCase;
    private final PedidoReceiverMapper pedidoMapper;

    public PedidoReceiverController(PedidoReceiverServiceUseCase receberPedidoUseCase, PedidoReceiverMapper pedidoMapper) {
        this.receberPedidoUseCase = receberPedidoUseCase;
        this.pedidoMapper = pedidoMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void receberPedido(@RequestBody PedidoReceiverRequestDTO pedidoRequestDTO) {
        log.info("Recebendo pedido: {}", pedidoRequestDTO);
        var pedido = pedidoMapper.toPedido(pedidoRequestDTO);
        receberPedidoUseCase.processar(pedido);
    }
}
