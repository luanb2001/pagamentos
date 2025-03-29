package com.project.pagamentos.adapters.in.api;

import com.project.pagamentos.application.usecase.ListarPagamentosUseCase;
import com.project.pagamentos.application.usecase.ProcessarPagamentoUseCase;
import com.project.pagamentos.domain.dto.PagamentoDTO;
import com.project.pagamentos.domain.dto.ProcessarPagamentoDTO;
import com.project.pagamentos.domain.enums.StatusPagamentoEnum;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/pagamento")
public class PagamentoController {

    private final ProcessarPagamentoUseCase processarPagamentoUseCase;
    private final ListarPagamentosUseCase listarPagamentosUseCase;

    public PagamentoController(ProcessarPagamentoUseCase processarPagamentoUseCase, ListarPagamentosUseCase listarPagamentosUseCase) {
        this.processarPagamentoUseCase = processarPagamentoUseCase;
        this.listarPagamentosUseCase = listarPagamentosUseCase;
    }

    @PostMapping
    public ResponseEntity<Boolean> processarPagamento(@RequestBody ProcessarPagamentoDTO processarPagamentoDTO) {
        this.processarPagamentoUseCase.executar(processarPagamentoDTO);
        return ResponseEntity.ok(true);
    }

    @GetMapping(path = "listar-pagamentos")
    public ResponseEntity<List<PagamentoDTO>> listarPagamentos(
            @RequestParam(name = "data-criacao-inicial") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataCriacaoInicial,
            @RequestParam(name = "data-criacao-final") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataCriacaoFinal,
            @RequestParam(name = "status-pagamento", required = false) StatusPagamentoEnum statusPagamentoEnum
    ) {
        List<PagamentoDTO> listaDePagamentos = this.listarPagamentosUseCase.executar(dataCriacaoInicial, dataCriacaoFinal, statusPagamentoEnum);
        return ResponseEntity.ok(listaDePagamentos);
    }
}
