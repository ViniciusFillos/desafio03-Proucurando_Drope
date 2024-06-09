package com.microServiceVotacao.services;

import com.microServiceVotacao.client.ClientProposta;
import com.microServiceVotacao.entities.Votacao;
import com.microServiceVotacao.exceptions.EntityNullException;
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

    private final ClientProposta clientProposta;

    public List<Votacao> findAll() {
        return votacaoRepository.findAll();
    }

    public ResultadoVotacaoDto encerrar() {
        if (votacao.getIdProposta() == null || votacao.getVotosContras() == null || votacao.getVotosPositivos() == null)
            throw new EntityNullException("Nenhuma votação está ativa no momento!");
        votacaoRepository.save(votacao);
        ResultadoVotacaoDto resultado = new ResultadoVotacaoDto();

        resultado.setIdProposta(votacao.getIdProposta());
        resultado.setVotosPositivos(votacao.getVotosPositivos());
        resultado.setVotosContras(votacao.getVotosContras());
        if (votacao.getVotosPositivos().equals(votacao.getVotosContras())) resultado.setResultado("Empate!");
        if (votacao.getVotosPositivos() > votacao.getVotosContras()) resultado.setResultado("Aprovada!");
        if (votacao.getVotosPositivos() < votacao.getVotosContras()) resultado.setResultado("Rejeitada!");
        clientProposta.mudarStatusVotacaoAtivo();

        deixarVotacaoNula();
        return resultado;
    }

    public void iniciarVotacao(Long idProposta) {
        votacao.setIdProposta(idProposta);
        votacao.setVotosPositivos(0);
        votacao.setVotosContras(0);
    }

    public void deixarVotacaoNula() {
        votacao.setIdProposta(null);
        votacao.setVotosPositivos(null);
        votacao.setVotosContras(null);
    }
}
