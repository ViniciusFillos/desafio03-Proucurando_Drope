package com.microServiceVotacao.services;

import com.microServiceVotacao.entities.Votacao;
import com.microServiceVotacao.exceptions.ActiveVotingException;
import com.microServiceVotacao.exceptions.InvalidIdVotingExcption;
import com.microServiceVotacao.exceptions.UnableVotingException;
import com.microServiceVotacao.exceptions.UniqueVoteException;
import com.microServiceVotacao.repositories.VotacaoRepository;
import com.microServiceVotacao.web.dto.VotoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VotacaoService {

    private final VotacaoRepository votacaoRepository;
    private Set<Long> idFuncionariosVotaram;
    private Boolean votacaoAtiva = false;

    public Votacao iniciar(Long idProposta) {
        if (votacaoAtiva) throw new ActiveVotingException();
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

    public Votacao encerrar(Long idVotacao) {
        Votacao votacao = votacaoRepository.findById(idVotacao).orElseThrow(InvalidIdVotingExcption::new);
        if (!votacaoAtiva) throw new UnableVotingException();
        votacao.setAtiva(false);
        votacaoRepository.save(votacao);
        votacaoAtiva = false;
        return votacao;
    }
}
