package com.microServiceVotacao.client;

import org.reactivestreams.Publisher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "proposta-consumer", url = "http://localhost:8080/api/v1/propostas")
public interface ClientProposta {

    @PostMapping("/votacao/encerrar")
    Publisher<?> mudarStatusVotacaoAtivo();
}
