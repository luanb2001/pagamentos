package com.project.pagamentos.adapters.out.messaging;

import com.project.pagamentos.domain.dto.PagamentoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PagamentoProducer {

    private static final Logger logger = LoggerFactory.getLogger(PagamentoProducer.class);
    private final RabbitTemplate rabbitTemplate;

    private static final String PAGAMENTO_QUEUE = "pagamento-queue";

    public PagamentoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarPagamento(PagamentoDTO pagamentoDTO) {
        PagamentoProducer.logger.info("📤 Enviando pagamento para RabbitMQ: {}", pagamentoDTO.id());
        this.rabbitTemplate.convertAndSend(PAGAMENTO_QUEUE, pagamentoDTO);
    }
}
