package com.microServiceVotacao.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microServiceVotacao.web.dto.ResultadoVotacaoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VotacaoProducer {

    @Value("votacao")
    private String votacaoKafka;
    private final ObjectMapper mapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public String enviarResultadoVotacao(ResultadoVotacaoDto resultadoVotacaoDto) throws JsonProcessingException {
        String conteudo = mapper.writeValueAsString(resultadoVotacaoDto);
        kafkaTemplate.send(votacaoKafka, conteudo);
        return "RESULTADO ENVIADO COM SUCESSO!";
    }
}