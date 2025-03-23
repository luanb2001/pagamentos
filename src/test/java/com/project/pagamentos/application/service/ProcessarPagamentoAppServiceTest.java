package com.project.pagamentos.application.service;

import com.project.pagamentos.adapters.out.messaging.PagamentoProducer;
import com.project.pagamentos.domain.dto.PagamentoDTO;
import com.project.pagamentos.domain.dto.ProcessarPagamentoDTO;
import com.project.pagamentos.domain.entity.Pagamento;
import com.project.pagamentos.domain.enums.StatusPagamentoEnum;
import com.project.pagamentos.domain.repository.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProcessarPagamentoAppServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private PagamentoProducer pagamentoProducer;

    @InjectMocks
    private ProcessarPagamentoAppService processarPagamentoAppService;

    private ProcessarPagamentoDTO processarPagamentoDTO;
    private Pagamento pagamento;

    @BeforeEach
    public void setUp() {
        UUID contaId = UUID.randomUUID();
        Double valor = 100.0;
        this.processarPagamentoDTO = new ProcessarPagamentoDTO(contaId, valor);

        this.pagamento = new Pagamento();
        this.pagamento.setContaId(contaId);
        this.pagamento.setValor(valor);
        this.pagamento.setStatus(StatusPagamentoEnum.PENDENTE);
    }

    @Test
    public void deveProcessarPagamentoComSucesso() {
        when(this.pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);

        this.processarPagamentoAppService.executar(this.processarPagamentoDTO);

        verify(this.pagamentoRepository, times(1)).save(any(Pagamento.class));
        verify(this.pagamentoProducer, times(1)).enviarPagamento(any(PagamentoDTO.class));
    }

    @Test
    public void deveChamarMetodoPersistirPagamento() {
        when(this.pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);

        this.processarPagamentoAppService.executar(this.processarPagamentoDTO);

        verify(this.pagamentoRepository, times(1)).save(any(Pagamento.class));
    }

    @Test
    public void deveEnviarPagamentoParaFila() {
        when(this.pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);

        this.processarPagamentoAppService.executar(this.processarPagamentoDTO);

        verify(this.pagamentoProducer, times(1)).enviarPagamento(any(PagamentoDTO.class));
    }

}
