package com.project.pagamentos.adapters.out.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pagamentos.domain.dto.PagamentoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class PagamentoProducer {

    private static final Logger logger = LoggerFactory.getLogger(PagamentoProducer.class);
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    private static final String PAGAMENTO_QUEUE = "pagamento-queue";

    public PagamentoProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void enviarPagamento(PagamentoDTO pagamentoDTO) {
        try {
            String pagamentoJson = this.objectMapper.writeValueAsString(pagamentoDTO);

            Message message = MessageBuilder.withBody(pagamentoJson.getBytes(StandardCharsets.UTF_8))
                    .setContentType("application/json")
                    .build();

            logger.info("Enviando pagamento para RabbitMQ: {}", pagamentoDTO.id());
            this.rabbitTemplate.convertAndSend(PAGAMENTO_QUEUE, message);

        } catch (JsonProcessingException e) {
            logger.error("Erro ao serializar pagamento para envio: {}", e.getMessage(), e);
        }
    }
}

