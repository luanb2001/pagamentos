package com.project.pagamentos.application.service;

import com.project.pagamentos.domain.enums.StatusPagamentoEnum;
import com.project.pagamentos.domain.repository.PagamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class ListarPagamentosAppServiceTest {

    @InjectMocks
    private ListarPagamentosAppService listarPagamentosAppService;

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Test
    public void executar() {
        assertDoesNotThrow(() -> this.listarPagamentosAppService.executar(
                LocalDateTime.now(),
                LocalDateTime.now(),
                StatusPagamentoEnum.CONFIRMADO));
    }
}
