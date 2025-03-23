package com.project.pagamentos.adapters.out.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.pagamentos.domain.dto.PagamentoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Component;

@Component
public class PagamentoProducer {

    private static final Logger logger = LoggerFactory.getLogger(PagamentoProducer.class);
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    private static final String PAGAMENTO_QUEUE = "pagamento-queue";

    public PagamentoProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        this.objectMapper = objectMapper;
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public void enviarPagamento(PagamentoDTO pagamentoDTO) {
        String pagamentoJson;
        try {
            pagamentoJson = this.objectMapper.writeValueAsString(pagamentoDTO);
        } catch (JsonProcessingException e) {
            PagamentoProducer.logger.info("Não foi possível realizar o envio do pagamento: ", e.getMessage());
            return;
        }

        Message message = MessageBuilder.withBody(pagamentoJson.getBytes())
                .setContentType("application/json")
                .build();

        PagamentoProducer.logger.info("Enviando pagamento para RabbitMQ: {}", pagamentoDTO.id());
        this.rabbitTemplate.convertAndSend(PAGAMENTO_QUEUE, message);
    }
}
