package com.fiap.pagamento.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class PedidoRequestDTO {
    private String clienteId;
    private List<ItemDTO> itens;

    @Data
    public static class ItemDTO {
        private String produtoId;
        private int quantidade;
    }
}