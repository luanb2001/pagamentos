package com.project.pagamentos.adapters.out.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.pagamentos.domain.dto.PagamentoDTO;
import com.project.pagamentos.domain.enums.StatusPagamentoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PagamentoProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    private ObjectMapper objectMapper;
    private PagamentoProducer pagamentoProducer;

    private static final String PAGAMENTO_QUEUE = "pagamento-queue";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());

        this.pagamentoProducer = new PagamentoProducer(this.rabbitTemplate, this.objectMapper);
    }

    @Test
    public void deveVerificarMensagemEnviada() throws Exception {
        PagamentoDTO pagamentoDTO = new PagamentoDTO(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(), null, 100.0, StatusPagamentoEnum.PENDENTE, "teste");

        String pagamentoJson = this.objectMapper.writeValueAsString(pagamentoDTO);
        byte[] pagamentoBytes = pagamentoJson.getBytes(StandardCharsets.UTF_8);

        this.pagamentoProducer.enviarPagamento(pagamentoDTO);

        ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);
        verify(this.rabbitTemplate, times(1)).convertAndSend(eq(PAGAMENTO_QUEUE), captor.capture());

        assertArrayEquals(pagamentoBytes, captor.getValue().getBody());
    }
}
