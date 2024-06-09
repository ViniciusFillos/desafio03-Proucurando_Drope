package com.microServiceVotacao.unitTests.services;

import com.microServiceVotacao.client.ClientProposta;
import com.microServiceVotacao.entities.Votacao;
import com.microServiceVotacao.exceptions.EntityNullException;
import com.microServiceVotacao.mocks.MockEntity;
import com.microServiceVotacao.repositories.VotacaoRepository;
import com.microServiceVotacao.services.VotacaoService;
import com.microServiceVotacao.web.dto.ResultadoVotacaoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
class VotacaoServiceTest {

    MockEntity input;

    @Mock
    VotacaoRepository votacaoRepository;

    @Mock
    ClientProposta clientProposta;

    @InjectMocks
    private VotacaoService votacaoService;

    @BeforeEach
    void setUpMocks() {
        input = new MockEntity();
    }

    @Test
    void findAll_RetornarListaVotacao() {
        List<Votacao> votacoes = input.mockVotacaoList();
        when(votacaoRepository.findAll()).thenReturn(votacoes);

        var listVotacoes = votacaoService.findAll();

        assertNotNull(listVotacoes);
        assertEquals(10, listVotacoes.size());
        var votacaoUm = listVotacoes.getFirst();
        var votacaoDez = listVotacoes.get(9);

        assertNotNull(votacaoUm);
        assertNotNull(votacaoUm.getId());
        assertNotNull(votacaoUm.getIdProposta());
        assertNotNull(votacaoUm.getVotosContras());
        assertNotNull(votacaoUm.getVotosPositivos());
        assertEquals(1, votacaoUm.getVotosContras());
        assertEquals(2, votacaoUm.getVotosPositivos());
        assertEquals(1L, votacaoUm.getIdProposta());

        assertNotNull(votacaoDez);
        assertNotNull(votacaoDez.getId());
        assertNotNull(votacaoDez.getIdProposta());
        assertNotNull(votacaoDez.getVotosContras());
        assertNotNull(votacaoDez.getVotosPositivos());
        assertEquals(1, votacaoDez.getVotosContras());
        assertEquals(2, votacaoDez.getVotosPositivos());
        assertEquals(1L, votacaoDez.getIdProposta());
    }

    @Test
    void encerrar_ComDadosValidos_RetornarResultado() {
        Votacao votacao = input.mockVotacao();
        when(votacaoRepository.save(any(Votacao.class))).thenReturn(votacao);
        votacaoService.iniciarVotacao(1L);

        ResultadoVotacaoDto resultado = votacaoService.encerrar();
        resultado.setIdProposta(votacao.getIdProposta());
        resultado.setVotosPositivos(votacao.getVotosPositivos());
        resultado.setVotosContras(votacao.getVotosContras());
        if (votacao.getVotosPositivos().equals(votacao.getVotosContras())) resultado.setResultado("Empate!");
        if (votacao.getVotosPositivos() > votacao.getVotosContras()) resultado.setResultado("Aprovada!");
        if (votacao.getVotosPositivos() < votacao.getVotosContras()) resultado.setResultado("Rejeitada!");

        assertNotNull(resultado);
        assertNotNull(resultado.getIdProposta());
        assertNotNull(resultado.getVotosPositivos());
        assertNotNull(resultado.getVotosContras());
        assertEquals(1, resultado.getIdProposta());
        assertEquals(2, resultado.getVotosPositivos());
        assertEquals(1, resultado.getVotosContras());
        assertEquals("Aprovada!", resultado.getResultado());
    }

    @Test
    void encerrar_ComDadosInvalidos_RetornarException() {
        Exception e = assertThrows(EntityNullException.class, () -> votacaoService.encerrar());

        assertEquals("Nenhuma votação está ativa no momento!", e.getMessage());
    }

    @Test
    void iniciarVotacao_ComIdValido_DeveInicializarVotacao() {
        votacaoService.iniciarVotacao(1L);

        assertEquals(1L, VotacaoService.votacao.getIdProposta());
        assertEquals(0, VotacaoService.votacao.getVotosPositivos());
        assertEquals(0, VotacaoService.votacao.getVotosContras());
    }

    @Test
    void deixarVotacaoNula_DeveDeixarVotacaoComValoresNulos() {
        votacaoService.deixarVotacaoNula();

        assertNull(VotacaoService.votacao.getIdProposta());
        assertNull(VotacaoService.votacao.getVotosPositivos());
        assertNull(VotacaoService.votacao.getVotosContras());
    }
}
