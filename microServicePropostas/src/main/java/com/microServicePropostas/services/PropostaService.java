package com.microServicePropostas.services;

import com.microServicePropostas.client.FuncionarioClient;
import com.microServicePropostas.client.VotacaoClient;
import com.microServicePropostas.exception.EntityInvalidException;
import com.microServicePropostas.entities.Proposta;
import com.microServicePropostas.exception.VotacaoAtivaException;
import com.microServicePropostas.exception.VotacaoExpiradaException;
import com.microServicePropostas.exception.VotoUnicoException;
import com.microServicePropostas.producer.VotoProducer;
import com.microServicePropostas.repositories.PropostaRepository;
import com.microServicePropostas.validacao.Validar;
import com.microServicePropostas.web.dto.PropostaDto;
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
    private List<Long> idFuncionariosVotoRegistrado;

    public Proposta save(Proposta proposta) {
        Validar.validarProposta(proposta);
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

    public Proposta update(Long id, PropostaDto propostaDto) {
        Validar.validarProposta(propostaDto);
        Proposta newProposta;
        try {
            newProposta = findById(id);
        } catch (Exception e) {
            throw new EntityInvalidException("Proposta não encontrada!");
        }
        newProposta.setTitulo(propostaDto.getTitulo());
        newProposta.setDescricao(propostaDto.getDescricao());
        propostaRepository.save(newProposta);
        return newProposta;
    }

    public void delete(Long id) {
        try {
             findById(id);
        } catch (Exception e) {
            throw new EntityInvalidException("Proposta não encontrada!");
        }
        propostaRepository.deleteById(id);
    }

    public VotacaoDto iniciarVotacao(Long idProposta, Integer limite) {
        Proposta proposta = findById(idProposta);
        if (votacaoAtiva) throw new VotacaoAtivaException("Outra votação está ativa no momento!");

        votacaoClient.iniciarVotacao(idProposta);
        votacaoAtiva = true;
        idFuncionariosVotoRegistrado = new ArrayList<>();

        VotacaoDto.iniciarVotacao(votacaoDto, proposta, limite);
        return votacaoDto;
    }

    public void mudarStatusVotacaoAtivo() {
        votacaoAtiva = false;
    }

    public VotoDto votar(VotoDto votoDto) {
        if (!votacaoAtiva) throw new VotacaoAtivaException("Nenhuma votação está ativa no momento!");
        Validar.validarVoto(votoDto);
        try {
            funcionarioClient.buscarPorId(votoDto.getIdFuncionario());
        } catch (Exception e) {
            throw new EntityInvalidException("Funcionário inválido!");
        }
        if (idFuncionariosVotoRegistrado.contains(votoDto.getIdFuncionario())) throw new VotoUnicoException();
        if (votacaoDto.getDataCriacao().isBefore(LocalDateTime.now())) throw new VotacaoExpiradaException();
        idFuncionariosVotoRegistrado.add(votoDto.getIdFuncionario());
        return votoDto;
    }

    public String integrarVoto(VotoDto voto) {
        try {
            return votoProducer.enviarVoto(voto);
        } catch (Exception e) {
            throw new EntityInvalidException("Erro ao enviar voto!");
        }
    }
}
