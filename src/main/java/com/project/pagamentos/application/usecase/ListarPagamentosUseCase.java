package com.project.pagamentos.application.usecase;

import com.project.pagamentos.domain.dto.PagamentoDTO;
import com.project.pagamentos.domain.enums.StatusPagamentoEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ListarPagamentosUseCase {

    public List<PagamentoDTO> executar(
            LocalDateTime dataCriacaoInicial,
            LocalDateTime dataCriacaoFinal,
            StatusPagamentoEnum statusPagamentoEnum
    );
}
