package com.microServicePropostas.consumer;

import com.google.gson.Gson;
import com.microServicePropostas.web.dto.ResultadoVotacaoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResultadoConsumer {

    private final Gson gson;

    @KafkaListener(
            topics = "${topicos.request.votacao}", // Para escutar o tópico "votacao"
            groupId = "topic-votacao"
    )
    public void consumer(String resultadoString){
        ResultadoVotacaoDto resultadoVotacaoDto = gson.fromJson(resultadoString, ResultadoVotacaoDto.class);
        resultadoVotacaoDto.setResultado();
        log.info("VOTAÇÃO ENCERRADA!:   "+ resultadoVotacaoDto.getResultado());
    }
}
