package com.fiap.receiver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoReceiver {
    private String clienteId;
    private List<Item> itens;
    private Pagamento dadosPagamento;

    @Data
    public static class Item {
        private String produtoId;
        private int quantidade;
    }

    @Data
    public static class Pagamento {
        private String numeroCartao;
    }
}