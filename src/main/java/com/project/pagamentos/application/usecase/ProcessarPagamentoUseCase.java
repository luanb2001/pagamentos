package com.project.pagamentos.application.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.pagamentos.domain.dto.ProcessarPagamentoDTO;

public interface ProcessarPagamentoUseCase {

    void executar(ProcessarPagamentoDTO processarPagamentoDTO) throws JsonProcessingException;
}
