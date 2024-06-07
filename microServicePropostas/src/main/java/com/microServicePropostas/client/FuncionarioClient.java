package com.microServicePropostas.client;

import com.microServicePropostas.web.dto.FuncionarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "funcionario-consumer", url = "http://localhost:8080/api/v1/funcionarios")
public interface FuncionarioClient {

    @GetMapping("/{id}")
    public FuncionarioDto buscarPorId(@PathVariable("id") Long id);

}
