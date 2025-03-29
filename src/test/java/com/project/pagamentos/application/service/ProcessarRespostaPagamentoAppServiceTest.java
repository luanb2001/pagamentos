package com.project.pagamentos.application.service;

import com.project.pagamentos.application.usecase.BuscarPagamentoPorIdUseCase;
import com.project.pagamentos.domain.dto.PagamentoRespostaDTO;
import com.project.pagamentos.domain.entity.Pagamento;
import com.project.pagamentos.domain.enums.StatusPagamentoEnum;
import com.project.pagamentos.domain.repository.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProcessarRespostaPagamentoAppServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private BuscarPagamentoPorIdUseCase buscarPagamentoPorIdUseCase;

    @InjectMocks
    private ProcessarRespostaPagamentoAppService processarRespostaPagamentoAppService;

    private UUID contaId;
    private UUID pagamentoId;
    private LocalDateTime dataProcessamento;

    @BeforeEach
    public void setUp() {
        this.contaId = UUID.randomUUID();
        this.pagamentoId = UUID.randomUUID();
        this.dataProcessamento = LocalDateTime.now();
    }

    @Test
    public void deveAtualizarStatusPagamentoComSucesso() {
        PagamentoRespostaDTO pagamentoRespostaDTO = new PagamentoRespostaDTO(this.pagamentoId, this.contaId, this.dataProcessamento, StatusPagamentoEnum.CONFIRMADO, null);

        Pagamento pagamento = new Pagamento();
        pagamento.setId(this.pagamentoId);
        pagamento.setContaId(this.contaId);
        pagamento.setStatus(StatusPagamentoEnum.PENDENTE);
        pagamento.setMotivoRecusa(null);

        when(this.buscarPagamentoPorIdUseCase.executar(this.pagamentoId)).thenReturn(Optional.of(pagamento));
        when(this.pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);

        this.processarRespostaPagamentoAppService.executar(pagamentoRespostaDTO);

        assertEquals(StatusPagamentoEnum.CONFIRMADO, pagamento.getStatus());
        assertEquals(this.dataProcessamento, pagamento.getDataProcessamento());
        verify(this.pagamentoRepository, times(1)).save(pagamento);
    }

    @Test
    public void naoDeveAtualizarStatusQuandoPagamentoNaoExistir() {
        String mensagemRecusa = "Pagamento não encontrado";

        PagamentoRespostaDTO pagamentoRespostaDTO = new PagamentoRespostaDTO(this.pagamentoId, this.contaId, this.dataProcessamento, StatusPagamentoEnum.RECUSADO, mensagemRecusa);

        when(this.buscarPagamentoPorIdUseCase.executar(this.pagamentoId)).thenReturn(Optional.empty());

        this.processarRespostaPagamentoAppService.executar(pagamentoRespostaDTO);

        verify(this.pagamentoRepository, never()).save(any(Pagamento.class));
    }

    @Test
    public void deveAtualizarStatusQuandoPagamentoExistirEStatusForDiferente() {
        String mensagemRecusa = "Pagamento rejeitado";

        PagamentoRespostaDTO pagamentoRespostaDTO = new PagamentoRespostaDTO(this.pagamentoId, this.contaId, this.dataProcessamento, StatusPagamentoEnum.RECUSADO, mensagemRecusa);

        Pagamento pagamento = new Pagamento();
        pagamento.setId(this.pagamentoId);
        pagamento.setStatus(StatusPagamentoEnum.PENDENTE);

        when(this.buscarPagamentoPorIdUseCase.executar(this.pagamentoId)).thenReturn(Optional.of(pagamento));

        this.processarRespostaPagamentoAppService.executar(pagamentoRespostaDTO);

        assertEquals(StatusPagamentoEnum.RECUSADO, pagamento.getStatus());
        assertEquals(mensagemRecusa, pagamento.getMotivoRecusa());
        verify(this.pagamentoRepository, times(1)).save(pagamento);
    }
}
