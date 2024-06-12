package com.microServiceVotacao.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microServiceVotacao.entities.Votacao;
import com.microServiceVotacao.mocks.MockEntity;
import com.microServiceVotacao.repositories.VotacaoRepository;
import com.microServiceVotacao.services.VotacaoService;
import com.microServiceVotacao.web.controller.VotacaoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SpringJUnitConfig
class VotacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    VotacaoController votacaoController;

    @Autowired
    private VotacaoRepository votacaoRepository;

    MockEntity input;

    @BeforeEach
    public void setUp() {
        input = new MockEntity();
    }

    @Test
    @Order(1)
    @Sql({"/delete.sql", "/create.sql", "/insert.sql"})
    void getAll_ReturnaVotacaoList() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/votacao")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<Votacao> votacoes = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertThat(votacoes).isNotEmpty();
        Votacao votacao1 = votacoes.getFirst();
        assertThat(votacao1.getId()).isEqualTo(1L);
        assertThat(votacao1.getIdProposta()).isEqualTo(1L);
        assertThat(votacao1.getVotosContras()).isEqualTo(5);
        assertThat(votacao1.getVotosPositivos()).isEqualTo(10);
    }

    @Test
    @Order(2)
    @Sql({"/delete.sql", "/create.sql", "/insert.sql"})
    void getById_ReturnaVotacao() throws Exception {
        Votacao votacao = votacaoRepository.findById(1L).orElseThrow();

        MvcResult result = mockMvc.perform(get("/api/v1/votacao/{id}", votacao.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Votacao votacaoRetornada = objectMapper.readValue(result.getResponse().getContentAsString(), Votacao.class);

        assertThat(votacaoRetornada).isEqualTo(votacao);
    }

    @Test
    @Order(3)
    void iniciarVotacao_ComDadosValidos_RetornaVotacao() throws Exception {
        Long idProposta = 1L;

        mockMvc.perform(post("/api/v1/votacao/iniciar/{idProposta}", idProposta)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Votacao votacao = VotacaoService.votacao;
        assertThat(votacao.getIdProposta()).isEqualTo(idProposta);
        assertThat(votacao.getVotosPositivos()).isZero();
        assertThat(votacao.getVotosContras()).isZero();
    }
}