package com.project.pagamentos.application.usecase;

import com.project.pagamentos.domain.dto.ProcessarPagamentoDTO;

public interface ProcessarPagamentoUseCase {

    void executar(ProcessarPagamentoDTO processarPagamentoDTO);
}
