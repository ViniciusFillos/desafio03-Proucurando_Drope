package com.microServicePropostas.web.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microServicePropostas.entities.Proposta;
import com.microServicePropostas.mocks.MockProposta;
import com.microServicePropostas.repositories.PropostaRepository;
import com.microServicePropostas.services.PropostaService;
import com.microServicePropostas.web.dto.PropostaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SpringJUnitConfig
class PropostaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private PropostaRepository propostaRepository;

    MockProposta input;

    private final ModelMapper modelMapper = new ModelMapper();
    private final Long ID_VALIDO = 1L;
    private final Long ID_INVALIDO = 9999L;

    @Autowired
    private PropostaService propostaService;

    @BeforeEach
    public void setUp() {
        input = new MockProposta();
    }

    @Test
    @Order(1)
    @Sql({"/delete.sql", "/create.sql","/insert.sql"})
    void getAll_ReturnarPropostasList() throws Exception {
        List<Proposta> propostas = input.mockEntityList();

        MvcResult result = mockMvc.perform(get("/api/v1/propostas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<Proposta> propostasRetornadas = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertThat(propostasRetornadas).isEqualTo(propostas);
    }

    @Test
    @Order(2)
    void criarProposta_ComDadosValidos_RetornarProposta() throws Exception {
        Proposta proposta = input.mockEntity();
        String propostaJson = objectMapper.writeValueAsString(proposta);

        MvcResult result = mockMvc.perform(post("/api/v1/propostas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(propostaJson))
                .andExpect(status().isCreated())
                .andReturn();

        Proposta propostaCriada = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Proposta.class);

        Proposta propostaSalva = propostaRepository.findById(propostaCriada.getId()).orElse(null);
        assertThat(propostaSalva).isNotNull();
        assertThat(propostaSalva.getTitulo()).isEqualTo(proposta.getTitulo());
        assertThat(propostaSalva.getDescricao()).isEqualTo(proposta.getDescricao());
    }

    @Test
    @Order(3)
    void criarProposta_ComDadosInvalidos_RetornarBadRequest() throws Exception {
        Proposta propostaInvalida = new Proposta();
        String propostaInvalidaJson = objectMapper.writeValueAsString(propostaInvalida);

        mockMvc.perform(post("/api/v1/propostas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(propostaInvalidaJson))
                .andExpect(status().isBadRequest());
    }


    @Test
    @Order(4)
    void obterProposta_Existente_RetornarProposta() throws Exception {
        Proposta proposta = modelMapper.map(input.mockDto(), Proposta.class);
        propostaRepository.save(proposta);

        MvcResult result = mockMvc.perform(get("/api/v1/propostas/{id}", proposta.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Proposta propostaRetornada = objectMapper.readValue(result.getResponse().getContentAsString(), Proposta.class);
        assertThat(propostaRetornada).isEqualTo(proposta);
    }

    @Test
    @Order(5)
    void obterProposta_ComIdInvalido_RetornarNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/propostas/{id}", ID_INVALIDO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(6)
    @Sql({"/delete.sql", "/create.sql", "/insert.sql"})
    void updateProposta_ComDadosValidos_RetornaProposta() throws Exception {
        PropostaDto propostaDto = input.mockDto();

        mockMvc.perform(put("/api/v1/propostas/{id}", ID_VALIDO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propostaDto)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(7)
    @Sql({"/delete.sql", "/create.sql", "/insert.sql"})
    void updateProposta_ComDadosinvalidos_ReturnsNotFound() throws Exception {
        PropostaDto propostaDto = input.mockDto();

        mockMvc.perform(put("/api/v1/propostas/{id}", ID_INVALIDO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propostaDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(8)
    @Sql({"/delete.sql", "/create.sql", "/insert.sql"})
    void deleteProposta_DeveExcluirPropostaExistente() throws Exception {
        assertDoesNotThrow(() -> mockMvc.perform(delete("/api/v1/propostas/{id}", ID_VALIDO)));

        mockMvc.perform(get("/api/v1/propostas/{id}", ID_VALIDO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(9)
    @Sql({"/delete.sql", "/create.sql", "/insert.sql"})
    void deleteProposta_DeveLancarExcecaoParaPropostaInexistente() {
        assertDoesNotThrow(() -> mockMvc.perform(delete("/api/v1/propostas/{id}", ID_INVALIDO)));
    }

    @Test
    @Order(10)
    void mudarStatusVotacaoAtivo_DeveDesativarVotacao() {
        propostaService.mudarStatusVotacaoAtivo();
        assertFalse(propostaService.votacaoAtiva);
    }
}