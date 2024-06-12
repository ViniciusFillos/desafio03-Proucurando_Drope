package com.microServicePropostas.mocks;

import com.microServicePropostas.entities.Proposta;
import com.microServicePropostas.web.dto.PropostaDto;
import com.microServicePropostas.web.dto.VotoDto;

import java.util.ArrayList;
import java.util.List;

public class MockProposta {

    public Proposta mockEntity(){
        return mockEntity(0);
    }

    public PropostaDto mockDto(){
        return mockDto(0);
    }

    public List<Proposta> mockEntityList() {
        List<Proposta> propostas = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            propostas.add(mockEntity(i));
        }
        return propostas;
    }

    public List<PropostaDto> mockDtoList() {
        List<PropostaDto> propostasDtos = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            propostasDtos.add(mockDto(i));
        }
        return propostasDtos;
    }

    public PropostaDto mockDto(Integer num) {
        PropostaDto dto = new PropostaDto();
        dto.setTitulo("Titulo do proposta " + num);
        dto.setDescricao("Descricao do proposta " + num);
        return dto;
    }

    public Proposta mockEntity(Integer num) {
        Proposta proposta = new Proposta();
        proposta.setId(num.longValue());
        proposta.setTitulo("Titulo do proposta " + num);
        proposta.setDescricao("Descricao do proposta " + num);
        return proposta;
    }

    public VotoDto mockVotoDto(Integer num) {
        VotoDto dto = new VotoDto();
        dto.setIdFuncionario(num.longValue());
        dto.setAprovo(true);
        return dto;
    }
}
