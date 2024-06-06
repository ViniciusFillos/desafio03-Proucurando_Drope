package com.microServiceVotacao.web.dto;

import lombok.Data;

@Data
public class ResultadoVotacaoDto {

    private Long id;
    private String titulo;
    private String descricao;
    private Integer votosPositivos;
    private Integer votosContra;
    private String resultado;
}
