package com.project.pagamentos.application.service;

import com.project.pagamentos.application.usecase.BuscarPagamentoPorIdUseCase;
import com.project.pagamentos.domain.entity.Pagamento;
import com.project.pagamentos.domain.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BuscarPagamentoPorIdUseCaseAppService implements BuscarPagamentoPorIdUseCase {

    private final PagamentoRepository pagamentoRepository;

    public BuscarPagamentoPorIdUseCaseAppService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @Override
    public Optional<Pagamento> executar(UUID pagamentoId) {
        return this.pagamentoRepository.findById(pagamentoId);
    }
}
