package com.microServiceVotacao.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class VotoConsumer {
    @KafkaListener(
            topics = "${topicos.request.topic}",
            groupId = "topic-voto"
    )
    public void consumer(String mensagem){
        System.out.println("##################### MENSAGEM RECEBIDA #####:  "+ mensagem);
    }
}
