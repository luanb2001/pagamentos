package com.project.pagamentos.adapters.in.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.pagamentos.application.usecase.ProcessarPagamentoUseCase;
import com.project.pagamentos.domain.dto.ProcessarPagamentoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pagamento")
public class PagamentoController {

    private final ProcessarPagamentoUseCase processarPagamentoUseCase;

    public PagamentoController(ProcessarPagamentoUseCase processarPagamentoUseCase) {
        this.processarPagamentoUseCase = processarPagamentoUseCase;
    }

    @PostMapping
    public ResponseEntity<Boolean> processarPagamento(@RequestBody ProcessarPagamentoDTO processarPagamentoDTO) {
        try {
            this.processarPagamentoUseCase.executar(processarPagamentoDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(true);
    }
}
