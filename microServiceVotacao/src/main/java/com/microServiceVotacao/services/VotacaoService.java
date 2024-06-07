package com.microServiceVotacao.services;

import com.microServiceVotacao.entities.Votacao;
import com.microServiceVotacao.repositories.VotacaoRepository;
import com.microServiceVotacao.web.dto.ResultadoVotacaoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VotacaoService {

    private final VotacaoRepository votacaoRepository;

    public static Votacao votacao = new Votacao();

    public List<Votacao> findAll() {
        return votacaoRepository.findAll();
    }

    public ResultadoVotacaoDto encerrar() {

        votacaoRepository.save(votacao);

        ResultadoVotacaoDto resultado = new ResultadoVotacaoDto();

        resultado.setIdProposta(votacao.getIdProposta());
        resultado.setVotosPositivos(votacao.getVotosPositivos());
        resultado.setVotosContra(votacao.getVotosContras());
        if (votacao.getVotosPositivos().equals(votacao.getVotosContras())) resultado.setResultado("Empate!");
        if (votacao.getVotosPositivos() > votacao.getVotosContras()) resultado.setResultado("Aprovada!");
        if (votacao.getVotosPositivos() < votacao.getVotosContras()) resultado.setResultado("Rejeitada!");

        return resultado;
    }

    public void iniciarVotacao(Long idProposta) {
        votacao.setIdProposta(idProposta);
        votacao.setVotosPositivos(0);
        votacao.setVotosContras(0);
    }
}
