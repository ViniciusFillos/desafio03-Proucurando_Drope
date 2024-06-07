package com.microServiceVotacao.web.dto;

import lombok.Data;

@Data
public class ResultadoVotacaoDto {

    private Long idProposta;
    private Integer votosPositivos;
    private Integer votosContra;
    private String resultado;
}
