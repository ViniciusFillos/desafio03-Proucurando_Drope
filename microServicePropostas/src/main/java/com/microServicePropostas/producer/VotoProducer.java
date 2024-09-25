package com.microServicePropostas.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microServicePropostas.web.dto.VotoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VotoProducer  {

    @Value("${topicos.request.voto}")
    private String votoKafka;
    private final ObjectMapper mapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public String enviarVoto(VotoDto votoDto) throws JsonProcessingException {
        String conteudo = mapper.writeValueAsString(votoDto);
        kafkaTemplate.send(votoKafka, conteudo);
        return "VOTO ENVIADO COM SUCESSO!";
    }
}