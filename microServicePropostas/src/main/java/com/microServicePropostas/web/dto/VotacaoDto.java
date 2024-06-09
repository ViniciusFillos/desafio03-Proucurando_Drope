package com.microServicePropostas.web.dto;

import com.microServicePropostas.entities.Proposta;
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

    public static void iniciarVotacao(VotacaoDto votacaoDto, Proposta proposta, Integer limite) {
        votacaoDto.setAtiva(true);
        votacaoDto.setVotosContras(0);
        votacaoDto.setVotosPositivos(0);
        votacaoDto.setIdProposta(proposta.getId());
        votacaoDto.setTitulo(proposta.getTitulo());
        votacaoDto.setDescricao(proposta.getDescricao());
        votacaoDto.setDataCriacao(LocalDateTime.now().plusMinutes(limite));
    }
}
