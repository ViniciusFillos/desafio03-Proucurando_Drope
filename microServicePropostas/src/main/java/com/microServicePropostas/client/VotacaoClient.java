package com.microServicePropostas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "funcionario-consumer", url = "http://localhost:8080/api/v1/funcionarios")
public interface VotacaoClient {

    @PostMapping("/iniciar/{idProposta}")
    void iniciarVotacao(@PathVariable Long idProposta);
}