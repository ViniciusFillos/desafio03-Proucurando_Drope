package com.microServiceVotacao.services;

import com.microServiceVotacao.client.ClientProposta;
import com.microServiceVotacao.entities.Votacao;
import com.microServiceVotacao.exceptions.EntityInvalidException;
import com.microServiceVotacao.exceptions.EntityNotFoundException;
import com.microServiceVotacao.exceptions.EntityNullException;
import com.microServiceVotacao.producer.VotacaoProducer;
import com.microServiceVotacao.repositories.VotacaoRepository;
import com.microServiceVotacao.web.dto.ResultadoVotacaoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class VotacaoService {

    private final VotacaoRepository votacaoRepository;
    private final VotacaoProducer votacaoProducer;
    private final Logger logger = Logger.getLogger(VotacaoService.class.getName());
    private final ClientProposta clientProposta;

    public static Votacao votacao;

    public List<Votacao> findAll() {
        logger.info("Buscando todas votações!");
        return votacaoRepository.findAll();
    }

    public ResultadoVotacaoDto encerrar() {
        logger.info("Encerrando a votação!");
        if (votacao == null || votacao.getVotosContras() == null || votacao.getVotosPositivos() == null)
            throw new EntityNullException("Nenhuma votação está ativa no momento!");
        votacaoRepository.save(votacao);
        ResultadoVotacaoDto resultado = new ResultadoVotacaoDto(votacao);
        clientProposta.mudarStatusVotacaoAtivo();
        deixarVotacaoNula();
        integrarResultadoVotacao(resultado);
        logger.info("VOTAÇÃO ENCERRADA!:   "+ resultado.getResultado());
        return resultado;
    }

    public void iniciarVotacao(Long idProposta) {
        votacao = new Votacao();
        logger.info("Iniciando uma votação!");
        votacao.setIdProposta(idProposta);
        votacao.setVotosPositivos(0);
        votacao.setVotosContras(0);
    }

    public void deixarVotacaoNula() {
        votacao.setIdProposta(null);
        votacao.setVotosPositivos(null);
        votacao.setVotosContras(null);
    }

    public Votacao findById(Long id) {
        logger.info("Buscando uma proposta!");
        return votacaoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Votação id=%s não encontrado!", id))
        );
    }

    public String integrarResultadoVotacao(ResultadoVotacaoDto resultadoVotacaoDto) {
        try {
            return votacaoProducer.enviarResultadoVotacao(resultadoVotacaoDto);
        } catch (Exception e) {
            throw new EntityInvalidException("Erro ao enviar resulatado da votação!");
        }
    }
}
