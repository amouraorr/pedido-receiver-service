package com.fiap.receiver.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoRequestDTO {
    private String produtoId;
    private Integer quantidade;
}