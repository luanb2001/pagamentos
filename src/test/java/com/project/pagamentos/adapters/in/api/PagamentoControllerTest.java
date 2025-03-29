package com.project.pagamentos.adapters.in.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pagamentos.application.usecase.ListarPagamentosUseCase;
import com.project.pagamentos.application.usecase.ProcessarPagamentoUseCase;
import com.project.pagamentos.domain.dto.ProcessarPagamentoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class PagamentoControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private ProcessarPagamentoUseCase processarPagamentoUseCase;

    @MockitoBean
    private ListarPagamentosUseCase listarPagamentosUseCase;

    @Test
    public void processarPagamento() throws Exception {
        ProcessarPagamentoDTO processarPagamentoDTO = new ProcessarPagamentoDTO(UUID.randomUUID(), 100.0);

        String processarPagamentoDtoString = this.mapper.writeValueAsString(processarPagamentoDTO);

        this.mock.perform(post("/pagamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(processarPagamentoDtoString))
                .andExpect(status().isOk())
                .andReturn();

        verify(this.processarPagamentoUseCase, times(1)).executar(any(ProcessarPagamentoDTO.class));
    }

    @Test
    public void ListarPagamentos() throws Exception {
        this.mock.perform(get("/pagamento/listar-pagamentos?data-criacao-final=2024-09-20T12:00:00&data-criacao-inicial=2024-09-20T12:00:00&status-pagamento=CONFIRMADO")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(this.listarPagamentosUseCase, times(1)).executar(any(), any(), any());
    }
}
