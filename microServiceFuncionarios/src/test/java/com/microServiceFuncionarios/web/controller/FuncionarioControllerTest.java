package com.microServiceFuncionarios.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microServiceFuncionarios.entities.Funcionario;
import com.microServiceFuncionarios.exceptions.CpfUniqueViolationException;
import com.microServiceFuncionarios.exceptions.EntityNotFoundException;
import com.microServiceFuncionarios.mocks.MockFuncionario;
import com.microServiceFuncionarios.service.FuncionarioService;
import com.microServiceFuncionarios.web.ErrorMessage;
import com.microServiceFuncionarios.web.dto.FuncionarioDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FuncionarioController.class)
public class FuncionarioControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private FuncionarioService funcionarioService;
    private FuncionarioDto funcionarioDto = new FuncionarioDto();
    private MockFuncionario mockFuncionario = new MockFuncionario();

    @Test
    void salvarFuncionario_ComDadosValidos_RetornarFuncionarioCriadoComStatus201() throws Exception {
        funcionarioDto = mockFuncionario.mockDto(1);
        when(funcionarioService.salvar(funcionarioDto)).thenReturn(mockFuncionario.mockEntity(1));
        MvcResult result = mockMvc.perform(post("/api/v1/funcionarios")
                        .content(objectMapper.writeValueAsString(funcionarioDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Funcionario funcionario = objectMapper.readValue(responseBody, Funcionario.class);
        org.assertj.core.api.Assertions.assertThat(funcionario).isNotNull();
        org.assertj.core.api.Assertions.assertThat(funcionario.getCpf()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(funcionario.getCpf()).isEqualTo("11111111111");
        org.assertj.core.api.Assertions.assertThat(funcionario.getNome()).isEqualTo("Nome 1");
        org.assertj.core.api.Assertions.assertThat(funcionario.getDataNascimento()).isNotNull();

    }

    @Test
    void salvarFuncionario_CpfJaCadsatrado_RetornarErroComStatus409() throws Exception {
        funcionarioDto = mockFuncionario.mockDto(1);
        FuncionarioDto funcionario2Dto = mockFuncionario.mockDto(1);
        when(funcionarioService.salvar(funcionario2Dto)).thenThrow(CpfUniqueViolationException.class);
        mockMvc.perform(post("/api/v1/funcionarios")
                        .content(objectMapper.writeValueAsString(funcionarioDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        MvcResult result = mockMvc.perform(post("/api/v1/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionario2Dto)))
                .andExpect(status().isConflict())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ErrorMessage errorMessage = objectMapper.readValue(responseBody, ErrorMessage.class);
        org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
        org.assertj.core.api.Assertions.assertThat(errorMessage.getStatus()).isEqualTo(409);

    }


    @Test
    void buscarFuncionario_ComIdValido_RetornarFuncionarioComStatus200() throws Exception {
        Funcionario funcionario = mockFuncionario.mockEntity(1);
        when(funcionarioService.buscarPorId(1L)).thenReturn(funcionario);
        MvcResult result = mockMvc.perform(get("/api/v1/funcionarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Funcionario response = objectMapper.readValue(responseBody, Funcionario.class);

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getCpf()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getCpf()).isEqualTo("11111111111");
        org.assertj.core.api.Assertions.assertThat(response.getNome()).isEqualTo("Nome 1");
        org.assertj.core.api.Assertions.assertThat(response.getDataNascimento()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getId()).isEqualTo(1);

    }

    @Test
    void buscarFuncionario_ComIdInvalido_RetornarEntityNotFoundExceptionComStatus404() throws Exception {
        when(funcionarioService.buscarPorId(0L)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get("/api/v1/funcionarios/0"))
                .andExpect(status().isNotFound());

    }
}