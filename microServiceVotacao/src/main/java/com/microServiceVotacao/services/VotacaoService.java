package com.microServiceVotacao.services;

import com.microServiceVotacao.client.FuncionarioClient;
import com.microServiceVotacao.client.PropostaClient;
import com.microServiceVotacao.entities.Votacao;
import com.microServiceVotacao.exceptions.*;
import com.microServiceVotacao.repositories.VotacaoRepository;
import com.microServiceVotacao.web.dto.Proposta;
import com.microServiceVotacao.web.dto.ResultadoVotacaoDto;
import com.microServiceVotacao.web.dto.VotoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VotacaoService {

    private final PropostaClient propostaClient;
    private final FuncionarioClient funcionarioClient;
    private final VotacaoRepository votacaoRepository;
    private Set<Long> idFuncionariosVotaram;
    private Boolean votacaoAtiva = false;

    public Votacao iniciar(Long idProposta) {
        if (votacaoAtiva) throw new ActiveVotingException();
        try {
            propostaClient.getById(idProposta);
        } catch (Exception e) {
            throw new InvalidIdPropostaException();
        }
        Votacao votacao = new Votacao();
        votacao.setAtiva(true);
        votacao.setIdProposta(idProposta);
        votacao.setVotosPositivos(0);
        votacao.setVotosContras(0);
        idFuncionariosVotaram = new HashSet<>();
        votacaoAtiva = true;
        return votacaoRepository.save(votacao);
    }

    public VotoDto votar(Long idVotacao, VotoDto voto) {
        Votacao votacao = votacaoRepository.findById(idVotacao).orElseThrow(InvalidIdVotingExcption::new);
        if (!votacao.getAtiva()) throw new UnableVotingException();
        try {
            funcionarioClient.buscarPorId(voto.getIdFuncionario());
        } catch (Exception e) {
            throw new InvalidIdFuncionarioException();
        }
        if (idFuncionariosVotaram.contains(voto.getIdFuncionario())) throw new UniqueVoteException();
        if (voto.getAceitado()) votacao.setVotosPositivos(votacao.getVotosPositivos()+1);
        else votacao.setVotosContras(votacao.getVotosContras()+1);
        idFuncionariosVotaram.add(voto.getIdFuncionario());
        votacaoRepository.save(votacao);
        return voto;
    }

    public List<Votacao> findAll() {
        return votacaoRepository.findAll();
    }

    public ResultadoVotacaoDto encerrar(Long idVotacao) {
        Votacao votacao = votacaoRepository.findById(idVotacao).orElseThrow(InvalidIdVotingExcption::new);
        if (!votacaoAtiva) throw new UnableVotingException();

        ResultadoVotacaoDto resultado = new ResultadoVotacaoDto();
        Proposta proposta = propostaClient.getById(votacao.getIdProposta());

        resultado.setId(votacao.getId());
        resultado.setVotosPositivos(votacao.getVotosPositivos());
        resultado.setVotosContra(votacao.getVotosContras());
        resultado.setTitulo(proposta.getTitulo());
        resultado.setDescricao(proposta.getDescricao());
        if (votacao.getVotosPositivos().equals(votacao.getVotosContras())) resultado.setResultado("Empate!");
        if (votacao.getVotosPositivos() > votacao.getVotosContras()) resultado.setResultado("Aprovada!");
        if (votacao.getVotosPositivos() < votacao.getVotosContras()) resultado.setResultado("Rejeitada!");

        votacao.setAtiva(false);
        votacaoRepository.save(votacao);
        votacaoAtiva = false;
        return resultado;
    }
}
