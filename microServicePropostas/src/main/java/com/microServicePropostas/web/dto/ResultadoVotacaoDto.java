package com.microServicePropostas.web.dto;

import lombok.Data;

@Data
public class ResultadoVotacaoDto {

    private Long idProposta;
    private Integer votosPositivos;
    private Integer votosContras;
    private String resultado;
}
