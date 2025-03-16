package com.project.pagamentos.application.service;

import com.project.pagamentos.application.usecase.BuscarPagamentoPorIdUseCase;
import com.project.pagamentos.domain.entity.Pagamento;
import com.project.pagamentos.domain.repository.PagamentoRepository;

import java.util.Optional;
import java.util.UUID;

public class BuscarPagamentoPorIdUseCaseAppService implements BuscarPagamentoPorIdUseCase {

    private final PagamentoRepository pagamentoRepository;

    public BuscarPagamentoPorIdUseCaseAppService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @Override
    public Pagamento executar(UUID pagamentoId) {
        Optional<Pagamento> pagamento = this.pagamentoRepository.findById(pagamentoId);
        return null;
    }
}
