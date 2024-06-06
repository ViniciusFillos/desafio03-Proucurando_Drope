package com.microServiceVotacao.client;

import com.microServiceVotacao.web.dto.Proposta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "proposta-consumer", url = "http://localhost:8080/api/v1/proposta")
public interface PropostaClient {

    @GetMapping("/{id}")
    Proposta getById(@PathVariable Long id);
}
