package web.controller;

import entities.Funcionarios;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.FuncionariosService;

@RestController
@RequestMapping("/api/v1/funcionarios")
public class FuncionarioController {
    private FuncionariosService funcionariosService;

    @PostMapping
    public ResponseEntity<Funcionarios> salvar (@RequestBody Funcionarios funcionario){
        Funcionarios funcionarioCriado = funcionariosService.save(funcionario);
        return new ResponseEntity<>(funcionarioCriado, HttpStatus.CREATED);
    }

}
