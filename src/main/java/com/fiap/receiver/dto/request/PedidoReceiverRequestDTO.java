package com.fiap.receiver.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class PedidoReceiverRequestDTO {
    private String clienteId;
    private List<ItemDTO> itens;
    private PagamentoDTO pagamento;

    @Data
    public static class ItemDTO {
        private String produtoId;
        private int quantidade;
    }

    @Data
    public static class PagamentoDTO {
        private String numeroCartao;
    }
}