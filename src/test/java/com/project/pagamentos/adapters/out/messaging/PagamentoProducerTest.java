package com.project.pagamentos.adapters.out.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pagamentos.domain.dto.PagamentoDTO;
import com.project.pagamentos.domain.enums.StatusPagamentoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PagamentoProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PagamentoProducer pagamentoProducer;

    private static final String PAGAMENTO_QUEUE = "pagamento-queue";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveVerificarMensagemEnviada() throws Exception {
        PagamentoDTO pagamentoDTO = new PagamentoDTO(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(), null, 100.0, StatusPagamentoEnum.PENDENTE, "teste");

        String pagamentoJson = "{ \"id\": \"" + pagamentoDTO.id() + "\", \"contaId\": \"" + pagamentoDTO.contaId() + "\", \"dataCriacao\": \"" + pagamentoDTO.dataCriacao() + "\" }";
        Mockito.when(this.objectMapper.writeValueAsString(pagamentoDTO)).thenReturn(pagamentoJson);

        this.pagamentoProducer.enviarPagamento(pagamentoDTO);

        verify(this.rabbitTemplate, times(1)).convertAndSend(PAGAMENTO_QUEUE, pagamentoJson.getBytes());
    }
}
