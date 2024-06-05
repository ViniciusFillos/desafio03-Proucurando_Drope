package com.microServicePropostas.dto;
import com.microServicePropostas.entities.Proposta;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import lombok.Data;

@Data
@RequiredArgsConstructor
public class PropostaDto {

    private String titulo;
    private String descricao;
    private static final ModelMapper modelMapper = new ModelMapper();

    public static PropostaDto toDto(Proposta proposta) {
        return modelMapper.map(proposta, PropostaDto.class);
    }

    public Proposta toEntity() {
        return modelMapper.map(this, Proposta.class);
    }

}
