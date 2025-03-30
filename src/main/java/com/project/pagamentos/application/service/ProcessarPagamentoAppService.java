package com.project.pagamentos.application.service;

import com.project.pagamentos.adapters.out.messaging.PagamentoProducer;
import com.project.pagamentos.application.usecase.ProcessarPagamentoUseCase;
import com.project.pagamentos.domain.dto.PagamentoDTO;
import com.project.pagamentos.domain.dto.ProcessarPagamentoDTO;
import com.project.pagamentos.domain.entity.Pagamento;
import com.project.pagamentos.domain.repository.PagamentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProcessarPagamentoAppService implements ProcessarPagamentoUseCase {

    private static final Logger logger = LoggerFactory.getLogger(ProcessarPagamentoAppService.class);

    private final PagamentoRepository pagamentoRepository;
    private final PagamentoProducer pagamentoProducer;

    public ProcessarPagamentoAppService(PagamentoRepository pagamentoRepository, PagamentoProducer pagamentoProducer) {
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoProducer = pagamentoProducer;
    }

    @Override
    public void executar(ProcessarPagamentoDTO processarPagamentoDTO) {
        Pagamento pagamento = this.pagamentoRepository.save(Pagamento.cadastrarPagamento(processarPagamentoDTO));
        this.enviarParaFila(PagamentoDTO.fromEntity(pagamento));
    }

    private void enviarParaFila(PagamentoDTO pagamentoDTO) {
        this.pagamentoProducer.enviarPagamento(pagamentoDTO);
        ProcessarPagamentoAppService.logger.info("Pagamento enviado para RabbitMQ: {}", pagamentoDTO.id());
    }
}
