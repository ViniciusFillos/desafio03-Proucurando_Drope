package com.microServicePropostas.client;

import com.microServicePropostas.web.dto.FuncionarioDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "funcionario-consumer", url = "http://localhost:8080/api/v1/funcionarios")
public interface FuncionarioClient {

    @GetMapping("/{id}")
    FuncionarioDto buscarPorId(@PathVariable("id") Long id);
}
