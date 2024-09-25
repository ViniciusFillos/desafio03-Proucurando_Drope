package com.microServiceFuncionarios.web.dto;

import lombok.Data;

@Data
public class ResultadoVotacaoDto {

    private Long idProposta;
    private Integer votosPositivos;
    private Integer votosContras;
    private String resultado;

    public void setResultado() {
        if (this.getVotosPositivos().equals(this.getVotosContras())) this.setResultado("EMPATE!");
        if (this.getVotosPositivos() > this.getVotosContras()) this.setResultado("APROVADA!");
        if (this.getVotosPositivos() < this.getVotosContras()) this.setResultado("REJEITADA!");
    }
}
