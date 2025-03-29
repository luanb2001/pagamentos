package com.project.pagamentos.application.service;

import com.project.pagamentos.application.usecase.BuscarPagamentoPorIdUseCase;
import com.project.pagamentos.application.usecase.ProcessarRespostaPagamentoUseCase;
import com.project.pagamentos.domain.dto.PagamentoRespostaDTO;
import com.project.pagamentos.domain.enums.StatusPagamentoEnum;
import com.project.pagamentos.domain.repository.PagamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProcessarRespostaPagamentoAppService implements ProcessarRespostaPagamentoUseCase {

    private final PagamentoRepository pagamentoRepository;
    private final BuscarPagamentoPorIdUseCase buscarPagamentoPorIdUseCase;

    public ProcessarRespostaPagamentoAppService(PagamentoRepository pagamentoRepository, BuscarPagamentoPorIdUseCase buscarPagamentoPorIdUseCase) {
        this.pagamentoRepository = pagamentoRepository;
        this.buscarPagamentoPorIdUseCase = buscarPagamentoPorIdUseCase;
    }

    @Override
    public void executar(PagamentoRespostaDTO pagamentoRespostaDTO) {
        this.buscarPagamentoPorIdUseCase.executar(pagamentoRespostaDTO.pagamentoId()).ifPresent(pagamento -> {
            pagamento.setDataProcessamento(pagamentoRespostaDTO.dataProcessamento());
            pagamento.setStatus(pagamentoRespostaDTO.status());

            if (!StatusPagamentoEnum.CONFIRMADO.equals(pagamentoRespostaDTO.status())) {
                pagamento.setMotivoRecusa(pagamentoRespostaDTO.mensagem());
            }

            this.pagamentoRepository.save(pagamento);
        });
    }
}
