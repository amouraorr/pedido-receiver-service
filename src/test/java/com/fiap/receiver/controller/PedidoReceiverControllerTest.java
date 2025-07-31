package com.fiap.receiver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.receiver.dto.request.PedidoReceiverRequestDTO;
import com.fiap.receiver.mapper.PedidoReceiverMapper;
import com.fiap.receiver.domain.PedidoReceiver;
import com.fiap.receiver.usecase.service.PedidoReceiverServiceUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PedidoReceiverControllerTest {

    @Mock
    private PedidoReceiverServiceUseCase receberPedidoUseCase;

    @Mock
    private PedidoReceiverMapper pedidoMapper;

    @InjectMocks
    private PedidoReceiverController pedidoReceiverController;

    @Mock
    private PedidoReceiver pedidoReceiverMock;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private PedidoReceiverRequestDTO pedidoRequestDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoReceiverController).build();
        objectMapper = new ObjectMapper();

        pedidoRequestDTO = criarPedidoRequestDTO();
    }

    private PedidoReceiverRequestDTO criarPedidoRequestDTO() {
        PedidoReceiverRequestDTO dto = new PedidoReceiverRequestDTO();
        dto.setClienteId("123");

        PedidoReceiverRequestDTO.ItemDTO item = new PedidoReceiverRequestDTO.ItemDTO();
        item.setProdutoId("PROD001");
        item.setQuantidade(2);

        PedidoReceiverRequestDTO.PagamentoDTO pagamento = new PedidoReceiverRequestDTO.PagamentoDTO();
        pagamento.setNumeroCartao("1234567890123456");

        dto.setItens(List.of(item));
        dto.setDadosPagamento(pagamento);

        return dto;
    }

    @Test
    void deveInstanciarControllerCorretamente() {
        assertNotNull(pedidoReceiverController);
    }

    @Test
    void deveReceberPedidoComSucesso() throws Exception {
        when(pedidoMapper.toPedido(any(PedidoReceiverRequestDTO.class))).thenReturn(pedidoReceiverMock);
        doNothing().when(receberPedidoUseCase).processar(any(PedidoReceiver.class));

        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoRequestDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveChamarMapperComParametroCorreto() throws Exception {
        when(pedidoMapper.toPedido(any(PedidoReceiverRequestDTO.class))).thenReturn(pedidoReceiverMock);
        doNothing().when(receberPedidoUseCase).processar(any(PedidoReceiver.class));

        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoRequestDTO)))
                .andExpect(status().isCreated());

        verify(pedidoMapper, times(1)).toPedido(any(PedidoReceiverRequestDTO.class));
    }

    @Test
    void deveChamarUseCaseComParametroCorreto() throws Exception {
        when(pedidoMapper.toPedido(any(PedidoReceiverRequestDTO.class))).thenReturn(pedidoReceiverMock);
        doNothing().when(receberPedidoUseCase).processar(any(PedidoReceiver.class));

        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoRequestDTO)))
                .andExpect(status().isCreated());

        verify(receberPedidoUseCase, times(1)).processar(pedidoReceiverMock);
    }

    @Test
    void deveProcessarFluxoCompletoCorretamente() throws Exception {
        when(pedidoMapper.toPedido(pedidoRequestDTO)).thenReturn(pedidoReceiverMock);
        doNothing().when(receberPedidoUseCase).processar(pedidoReceiverMock);

        pedidoReceiverController.receberPedido(pedidoRequestDTO);

        verify(pedidoMapper, times(1)).toPedido(pedidoRequestDTO);
        verify(receberPedidoUseCase, times(1)).processar(pedidoReceiverMock);
    }

    @Test
    void deveRetornarStatus201AoReceberPedido() throws Exception {
        when(pedidoMapper.toPedido(any(PedidoReceiverRequestDTO.class))).thenReturn(pedidoReceiverMock);
        doNothing().when(receberPedidoUseCase).processar(any(PedidoReceiver.class));

        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoRequestDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveExecutarMetodoReceberPedidoSemRetorno() throws Exception {
        when(pedidoMapper.toPedido(pedidoRequestDTO)).thenReturn(pedidoReceiverMock);
        doNothing().when(receberPedidoUseCase).processar(pedidoReceiverMock);

        pedidoReceiverController.receberPedido(pedidoRequestDTO);

        verify(pedidoMapper).toPedido(pedidoRequestDTO);
        verify(receberPedidoUseCase).processar(pedidoReceiverMock);
        verifyNoMoreInteractions(pedidoMapper, receberPedidoUseCase);
    }

    @Test
    void deveValidarConstrutor() {
        PedidoReceiverController controller = new PedidoReceiverController(receberPedidoUseCase, pedidoMapper);
        assertNotNull(controller);
    }

    @Test
    void deveProcessarPedidoComMultiplosItens() throws Exception {
        PedidoReceiverRequestDTO.ItemDTO item1 = new PedidoReceiverRequestDTO.ItemDTO();
        item1.setProdutoId("PROD001");
        item1.setQuantidade(2);

        PedidoReceiverRequestDTO.ItemDTO item2 = new PedidoReceiverRequestDTO.ItemDTO();
        item2.setProdutoId("PROD002");
        item2.setQuantidade(1);

        pedidoRequestDTO.setItens(Arrays.asList(item1, item2));

        when(pedidoMapper.toPedido(any(PedidoReceiverRequestDTO.class))).thenReturn(pedidoReceiverMock);
        doNothing().when(receberPedidoUseCase).processar(any(PedidoReceiver.class));

        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoRequestDTO)))
                .andExpect(status().isCreated());

        verify(pedidoMapper).toPedido(any(PedidoReceiverRequestDTO.class));
        verify(receberPedidoUseCase).processar(pedidoReceiverMock);
    }
}