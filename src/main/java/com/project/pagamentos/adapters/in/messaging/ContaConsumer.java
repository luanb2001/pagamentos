package com.project.pagamentos.adapters.in.messaging;

import com.project.pagamentos.application.usecase.ProcessarRespostaPagamentoUseCase;
import com.project.pagamentos.domain.dto.PagamentoRespostaDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.project.pagamentos.infrastructure.config.RabbitMQConfig.CONTA_QUEUE;

@Component
public class ContaConsumer {

    private final ProcessarRespostaPagamentoUseCase processarRespostaPagamentoUseCase;

    public ContaConsumer(ProcessarRespostaPagamentoUseCase processarRespostaPagamentoUseCase) {
        this.processarRespostaPagamentoUseCase = processarRespostaPagamentoUseCase;
    }

    @RabbitListener(queues = CONTA_QUEUE)
    public void receberResposta(PagamentoRespostaDTO pagamentoRespostaDTO) {
        System.out.println("Resposta recebida do serviço de Contas: " + pagamentoRespostaDTO);
        this.processarRespostaPagamentoUseCase.executar(pagamentoRespostaDTO);
    }
}

