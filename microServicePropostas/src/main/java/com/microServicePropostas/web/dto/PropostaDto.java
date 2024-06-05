package com.microServicePropostas.web.dto;
import com.microServicePropostas.entities.Proposta;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import lombok.Data;

@Data
@RequiredArgsConstructor
public class PropostaDto {
    private String titulo;
    @NotBlank
    private String descricao;
}
