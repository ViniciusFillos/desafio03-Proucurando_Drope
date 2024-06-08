package com.microServicePropostas.services;

import com.microServicePropostas.client.FuncionarioClient;
import com.microServicePropostas.client.VotacaoClient;
import com.microServicePropostas.exception.EntityNullException;
import com.microServicePropostas.entities.Proposta;
import com.microServicePropostas.exception.VotacaoAtivaException;
import com.microServicePropostas.exception.VotacaoExpiradaException;
import com.microServicePropostas.exception.VotoUnicoException;
import com.microServicePropostas.producer.VotoProducer;
import com.microServicePropostas.repositories.PropostaRepository;
import com.microServicePropostas.web.dto.FuncionarioDto;
import com.microServicePropostas.web.dto.VotacaoDto;
import com.microServicePropostas.web.dto.VotoDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropostaService {

    private final FuncionarioClient funcionarioClient;
    private final VotacaoClient votacaoClient;
    private final PropostaRepository propostaRepository;
    private final VotoProducer votoProducer;
    private Boolean votacaoAtiva = false;
    private VotacaoDto votacaoDto = new VotacaoDto();
    private List<Long> idFuncionariosVotados;

    public Proposta save(Proposta proposta) {
        if (proposta == null) {
            throw new EntityNullException(String.format("Os campos não devem ser nulos!"));
        }
        return propostaRepository.save(proposta);
    }

    public List<Proposta> findAll() {
        return propostaRepository.findAll();
    }

    public Proposta findById(Long id) {
        return propostaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Proposta id=%s não encontrado!", id))
        );
    }

    public Proposta update(Long id, Proposta proposta) {
        Proposta prop = findById(id);
        prop.setId(proposta.getId());
        prop.setTitulo(proposta.getTitulo());
        prop.setDescricao(proposta.getDescricao());
        propostaRepository.save(prop);
        return prop;
    }

    public void delete(Long id) {
        propostaRepository.deleteById(id);
    }

    public VotacaoDto iniciarVotacao(Long idProposta) {
        Proposta prop = findById(idProposta);
        if (votacaoAtiva) throw new VotacaoAtivaException();
        votacaoClient.iniciarVotacao(idProposta);
        votacaoDto.setDataCriacao(LocalDateTime.now().plusMinutes(1));
        votacaoDto.setIdProposta(prop.getId());
        votacaoDto.setTitulo(prop.getTitulo());
        votacaoDto.setDescricao(prop.getDescricao());
        votacaoDto.setAtiva(true);
        votacaoDto.setVotosContras(0);
        votacaoDto.setVotosPositivos(0);
        votacaoAtiva = true;
        idFuncionariosVotados = new ArrayList<>();
        return votacaoDto;
    }

    public void mudarStatusVotacaoAtivo() {
        votacaoAtiva = false;
    }

    public VotoDto votar(VotoDto votoDto) {
        if (!votacaoAtiva) throw new VotacaoAtivaException();
        funcionarioClient.buscarPorId(votoDto.getIdFuncionario());
        if (idFuncionariosVotados.contains(votoDto.getIdFuncionario())) throw new VotoUnicoException();
        if (votacaoDto.getDataCriacao().isBefore(LocalDateTime.now())) throw new VotacaoExpiradaException();
        idFuncionariosVotados.add(votoDto.getIdFuncionario());
        // IMPLEMENTAR KAFKA: Aqui manda o voto pro kafka
        return votoDto;
    }

    public String integrarVoto(VotoDto voto) {
        try{
            return  votoProducer.enviarVoto(voto);
        }catch (Exception e){
            throw new EntityNullException("Erro ao enviar voto!");
        }
    }
}
