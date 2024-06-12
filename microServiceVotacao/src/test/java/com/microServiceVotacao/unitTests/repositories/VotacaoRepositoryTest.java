package com.microServiceVotacao.unitTests.repositories;

import com.microServiceVotacao.entities.Votacao;
import com.microServiceVotacao.mocks.MockEntity;
import com.microServiceVotacao.repositories.VotacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class VotacaoRepositoryTest {

    @Autowired
    private VotacaoRepository votacaoRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private MockEntity input;

    @BeforeEach
    void setUp() {
        input = new MockEntity();
    }

    @Test
    void salvarVotacao_ComDadosValidos_RegistrarVotacao() {
        Votacao votacao = votacaoRepository.save(input.mockVotacao());

        Votacao sut = testEntityManager.find(Votacao.class, votacao.getId());
        assertNotNull(sut);
        assertEquals(votacao, sut);
    }
}
