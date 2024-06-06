package com.microServiceVotacao.services;

import com.microServiceVotacao.entities.Votacao;
import com.microServiceVotacao.exceptions.*;
import com.microServiceVotacao.repositories.VotacaoRepository;
import com.microServiceVotacao.web.dto.ResultadoVotacaoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VotacaoService {

    private final VotacaoRepository votacaoRepository;
    private Boolean votacaoAtiva = false;

    public List<Votacao> findAll() {
        return votacaoRepository.findAll();
    }

    public ResultadoVotacaoDto encerrar(Long idVotacao) {
        Votacao votacao = votacaoRepository.findById(idVotacao).orElseThrow(InvalidIdVotingExcption::new);
        if (!votacaoAtiva) throw new UnableVotingException();

        ResultadoVotacaoDto resultado = new ResultadoVotacaoDto();

        resultado.setId(votacao.getId());
        resultado.setVotosPositivos(votacao.getVotosPositivos());
        resultado.setVotosContra(votacao.getVotosContras());
        if (votacao.getVotosPositivos().equals(votacao.getVotosContras())) resultado.setResultado("Empate!");
        if (votacao.getVotosPositivos() > votacao.getVotosContras()) resultado.setResultado("Aprovada!");
        if (votacao.getVotosPositivos() < votacao.getVotosContras()) resultado.setResultado("Rejeitada!");

        votacao.setAtiva(false);
        votacaoRepository.save(votacao);
        votacaoAtiva = false;
        return resultado;
    }
}
