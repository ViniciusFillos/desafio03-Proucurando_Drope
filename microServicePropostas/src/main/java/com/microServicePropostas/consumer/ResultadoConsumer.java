package com.microServicePropostas.consumer;

import com.google.gson.Gson;
import com.microServicePropostas.web.dto.ResultadoVotacaoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultadoConsumer {

    private final Gson gson;

    @KafkaListener(
            topics = "${topicos.request.votacao}", // Para escutar o tópico "votacao"
            groupId = "topic-votacao"
    )
    public void consumer(String resultadoString){
        ResultadoVotacaoDto resultadoVotacaoDto = gson.fromJson(resultadoString, ResultadoVotacaoDto.class);
        System.out.println("##### RESULTADO RECEBIDO #####:  "+ resultadoString);
    }
}
