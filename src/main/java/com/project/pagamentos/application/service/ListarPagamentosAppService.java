package com.project.pagamentos.application.service;

import com.project.pagamentos.application.usecase.ListarPagamentosUseCase;
import com.project.pagamentos.domain.dto.PagamentoDTO;
import com.project.pagamentos.domain.enums.StatusPagamentoEnum;
import com.project.pagamentos.domain.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ListarPagamentosAppService implements ListarPagamentosUseCase {

    private final PagamentoRepository pagamentoRepository;

    public ListarPagamentosAppService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @Override
    public List<PagamentoDTO> executar(LocalDateTime dataCriacaoInicial, LocalDateTime dataCriacaoFinal, StatusPagamentoEnum statusPagamentoEnum) {
        return this.pagamentoRepository.listarPagamentosPorFiltro(
                dataCriacaoInicial,
                dataCriacaoFinal,
                statusPagamentoEnum
        );
    }
}
