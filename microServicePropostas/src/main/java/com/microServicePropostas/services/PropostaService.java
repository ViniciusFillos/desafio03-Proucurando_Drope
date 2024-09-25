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
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class PropostaService {

    private final FuncionarioClient funcionarioClient;
    private final VotacaoClient votacaoClient;
    private final PropostaRepository propostaRepository;
    private final VotoProducer votoProducer;
    public Boolean votacaoAtiva = false;
    public Long votacaoAtivaId;
    private VotacaoDto votacaoDto = new VotacaoDto();
    private List<Long> idFuncionariosVotoRegistrado;
    private final Logger logger = Logger.getLogger(PropostaService.class.getName());

    public Proposta save(Proposta proposta) {
        logger.info("Salvando uma proposta!");
        Validar.validarProposta(proposta);
        return propostaRepository.save(proposta);
    }

    public List<Proposta> findAll() {
        logger.info("Buscando todas propostas!");
        return propostaRepository.findAll();
    }

    public Proposta findById(Long id) {
        logger.info("Buscando uma proposta!");
        return propostaRepository.findById(id).orElseThrow(
                () -> new com.microServicePropostas.exception.EntityNotFoundException(String.format("Proposta id=%s não encontrado!", id))
        );
    }

    public Proposta update(Long id, PropostaDto propostaDto) {
        logger.info("Atualizando uma proposta!");
        Validar.validarProposta(propostaDto);
        Proposta newProposta;
        try {
            newProposta = findById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Proposta não encontrada!");
        }
        newProposta.setTitulo(propostaDto.getTitulo());
        newProposta.setDescricao(propostaDto.getDescricao());
        propostaRepository.save(newProposta);
        return newProposta;
    }

    public void delete(Long id) {
        logger.info("Deletando uma proposta!");
        try {
            findById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Proposta não encontrada!");
        }
        propostaRepository.deleteById(id);
    }

    public VotacaoDto iniciarVotacao(Long idProposta, Integer limite) {
        logger.info("Iniciando uma votação!");
        Proposta proposta = findById(idProposta);
        if (votacaoAtiva) throw new VotacaoAtivaException("Outra votação está ativa no momento!");

        votacaoClient.iniciarVotacao(idProposta);
        votacaoAtiva = true;
        votacaoAtivaId = idProposta;
        idFuncionariosVotoRegistrado = new ArrayList<>();

        VotacaoDto.iniciarVotacao(votacaoDto, proposta, limite);
        return votacaoDto;
    }

    public void mudarStatusVotacaoAtivo(String resultado) {
        logger.info("Votação encerrada! " + resultado);
        funcionarioClient.votacaoEncerrada(resultado);
        votacaoAtiva = false;
    }

    public VotoDto votar(VotoDto votoDto) {
        logger.info("Registrando um voto!");
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
        votoDto.setTituloProposta(propostaRepository.findById(votacaoAtivaId).get().getTitulo());
        integrarVoto(votoDto);
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
