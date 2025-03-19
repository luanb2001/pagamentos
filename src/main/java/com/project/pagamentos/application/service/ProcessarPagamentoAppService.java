package com.project.pagamentos.application.service;

import com.project.pagamentos.adapters.out.messaging.PagamentoProducer;
import com.project.pagamentos.application.usecase.ProcessarPagamentoUseCase;
import com.project.pagamentos.domain.dto.PagamentoDTO;
import com.project.pagamentos.domain.dto.ProcessarPagamentoDTO;
import com.project.pagamentos.domain.entity.Pagamento;
import com.project.pagamentos.domain.enums.StatusPagamentoEnum;
import com.project.pagamentos.domain.repository.PagamentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

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
        UUID idPagamento = UUID.randomUUID();

        PagamentoDTO pagamentoDTO = new PagamentoDTO(
                idPagamento,
                processarPagamentoDTO.contaId(),
                LocalDateTime.now(),
                null,
                processarPagamentoDTO.valor(),
                StatusPagamentoEnum.PENDENTE,
                null);

        ProcessarPagamentoAppService.logger.info("Iniciando processamento do pagamento: {}", idPagamento);

        this.persistirPagamento(pagamentoDTO);
        this.enviarParaFila(pagamentoDTO);
    }

    private void persistirPagamento(PagamentoDTO pagamentoDTO ) {
        Pagamento pagamento = new Pagamento();

        pagamento.setId(pagamentoDTO.id());
        pagamento.setContaId(pagamentoDTO.contaId());
        pagamento.setDataCriacao(pagamentoDTO.dataCriacao());
        pagamento.setDataProcessamento(pagamentoDTO.dataProcessamento());
        pagamento.setValor(pagamentoDTO.valor());
        pagamento.setStatus(pagamentoDTO.status());
        pagamento.setMotivoRecusa(pagamentoDTO.motivoRecusa());

        this.pagamentoRepository.save(pagamento);
    }

    private void enviarParaFila(PagamentoDTO pagamentoDTO) {
        this.pagamentoProducer.enviarPagamento(pagamentoDTO);
        logger.info("Pagamento enviado para RabbitMQ: {}", pagamentoDTO.id());
    }
}
