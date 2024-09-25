package com.microServiceVotacao.web.dto;

import com.microServiceVotacao.entities.Votacao;
import lombok.Data;

@Data
public class ResultadoVotacaoDto {

    private Long idProposta;
    private Integer votosPositivos;
    private Integer votosContras;
    private String resultado;

    public ResultadoVotacaoDto(Votacao votacao) {
        this.idProposta = votacao.getIdProposta();
        this.votosPositivos = votacao.getVotosPositivos();
        this.votosContras = votacao.getVotosContras();
        this.setMyResultado(votacao);
    }

    public void setMyResultado(Votacao votacao) {
        if (votacao.getVotosPositivos().equals(votacao.getVotosContras())) this.setResultado("EMPATE!");
        if (votacao.getVotosPositivos() > votacao.getVotosContras()) this.setResultado("APROVADA!");
        if (votacao.getVotosPositivos() < votacao.getVotosContras()) this.setResultado("REJEITADA!");
    }
}
