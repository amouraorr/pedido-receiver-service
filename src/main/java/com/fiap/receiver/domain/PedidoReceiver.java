package com.fiap.receiver.domain;

import lombok.Data;
import java.util.List;

@Data
public class PedidoReceiver {
    private String clienteId;
    private List<Item> itens;
    private Pagamento pagamento;

    @Data
    public static class Item {
        private String produtoId;
        private int quantidade;
    }
}