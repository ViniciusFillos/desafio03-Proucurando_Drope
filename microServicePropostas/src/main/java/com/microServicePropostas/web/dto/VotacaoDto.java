package com.microServicePropostas.web.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VotacaoDto {

    private Long idProposta;
    private String titulo;
    private String descricao;
    private Integer votosPositivos;
    private Integer votosContras;
    private Boolean ativa;
    private LocalDateTime dataCriacao;

}
