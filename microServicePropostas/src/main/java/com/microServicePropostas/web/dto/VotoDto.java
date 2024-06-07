package com.microServicePropostas.web.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VotoDto {
    private Long idFuncionario;
    private Boolean aprovo;
}
