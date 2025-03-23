package com.project.pagamentos.adapters.in.messaging;

import com.project.pagamentos.application.usecase.ProcessarRespostaPagamentoUseCase;
import com.project.pagamentos.domain.dto.PagamentoRespostaDTO;
import com.project.pagamentos.domain.enums.StatusPagamentoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ContaConsumerTest {

    @Mock
    private ProcessarRespostaPagamentoUseCase processarRespostaPagamentoUseCase;

    @InjectMocks
    private ContaConsumer contaConsumer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveProcessarMensagemQuandoRecebida() {
        PagamentoRespostaDTO pagamentoRespostaDTO = new PagamentoRespostaDTO(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(), StatusPagamentoEnum.PENDENTE, null);

        this.contaConsumer.receberResposta(pagamentoRespostaDTO);

        verify(this.processarRespostaPagamentoUseCase, times(1)).executar(pagamentoRespostaDTO);
    }
}
