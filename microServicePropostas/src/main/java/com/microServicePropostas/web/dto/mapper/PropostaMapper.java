package com.microServicePropostas.web.dto.mapper;

import com.microServicePropostas.entities.Proposta;
import com.microServicePropostas.web.dto.PropostaDto;
import org.modelmapper.ModelMapper;

public class PropostaMapper {

    public static PropostaDto toDto(Proposta proposta) {
        return new ModelMapper().map(proposta, PropostaDto.class);
    }
    public static Proposta toProposta(PropostaDto propostaDto) {
        return new ModelMapper().map(propostaDto, Proposta.class);
    }

}
