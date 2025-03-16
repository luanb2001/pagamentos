package com.project.pagamentos.application.usecase;

import com.project.pagamentos.domain.dto.PagamentoRespostaDTO;

public interface ProcessarRespostaPagamentoUseCase {

    void executar(PagamentoRespostaDTO pagamentoRespostaDTO);
}
