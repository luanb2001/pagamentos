package com.project.pagamentos.application.service;

import com.project.pagamentos.domain.repository.PagamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class BuscarPagamentoPorIdAppServiceTest {

    @InjectMocks
    private BuscarPagamentoPorIdAppService buscarPagamentoPorIdAppService;

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Test
    public void executar() {
        assertDoesNotThrow(() -> this.buscarPagamentoPorIdAppService.executar(UUID.randomUUID()));
    }
}
