package com.microServiceVotacao.consumer;

import com.google.gson.Gson;
import com.microServiceVotacao.services.VotacaoService;
import com.microServiceVotacao.web.dto.VotoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VotoConsumer {

    private final Gson gson;

    @KafkaListener(
            topics = "${topicos.request.topic}",
            groupId = "topic-voto"
    )
    public void consumer(String votoString){

        VotoDto voto = gson.fromJson(votoString, VotoDto.class);
        if (voto.getAprovo()) VotacaoService.votacao.setVotosPositivos(VotacaoService.votacao.getVotosPositivos()+1);
        else VotacaoService.votacao.setVotosContras(VotacaoService.votacao.getVotosContras()+1);
        System.out.println("##### VOTO RECEBIDO #####:  "+ votoString);
    }
}
