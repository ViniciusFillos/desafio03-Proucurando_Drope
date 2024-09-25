package com.microServiceVotacao.web.dto;

import lombok.Data;

@Data
public class VotoDto {

    private Long idFuncionario;
    private String tituloProposta;
    private Boolean aprovo;
}
