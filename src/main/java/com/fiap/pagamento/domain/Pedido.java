package com.fiap.pagamento.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Pedido {
    private String clienteId;
    private List<Item> itens;

    @Data
    @AllArgsConstructor
    public static class Item {
        private String produtoId;
        private int quantidade;
    }
}
