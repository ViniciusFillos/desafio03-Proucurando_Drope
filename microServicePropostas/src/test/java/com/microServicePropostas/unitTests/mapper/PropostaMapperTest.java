package com.microServicePropostas.unitTests.mapper;

import com.microServicePropostas.entities.Proposta;
import com.microServicePropostas.mocks.MockProposta;
import com.microServicePropostas.web.dto.PropostaDto;
import com.microServicePropostas.web.dto.mapper.PropostaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropostaMapperTest {
    MockProposta input;

    @BeforeEach
    public void setUp() {
        input = new MockProposta();
    }

    @Test
    void transformarPropostaEmDto() {
        Proposta proposta = input.mockEntity(1);
        PropostaDto propostaDto = PropostaMapper.toDto(proposta);
        assertEquals(proposta.getTitulo(), propostaDto.getTitulo());
        assertEquals(proposta.getDescricao(), propostaDto.getDescricao());

    }
}
