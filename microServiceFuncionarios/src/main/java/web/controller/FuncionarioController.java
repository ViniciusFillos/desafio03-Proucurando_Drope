package web.controller;

import entities.Funcionarios;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.FuncionariosService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/funcionarios")
public class FuncionarioController {
    private FuncionariosService funcionariosService;

    @PostMapping
    public ResponseEntity<Funcionarios> salvar (@RequestBody Funcionarios funcionario){
        Funcionarios funcionarioCriado = funcionariosService.salvar(funcionario);
        return new ResponseEntity<>(funcionarioCriado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionarios> buscarPorId(@PathVariable Long id) {
        Funcionarios funcionario = funcionariosService.buscarPorId(id);
        return new ResponseEntity<>(funcionario, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Funcionarios>> buscarTodos() {
        List<Funcionarios> todosFuncionarios = funcionariosService.buscarTodos();
        return new ResponseEntity<>(todosFuncionarios, HttpStatus.OK);
    }

    @PatchMapping("inativarfuncionario/{id}")
    public ResponseEntity<Funcionarios> inativarFuncionario(@PathVariable  Long id) {
        Funcionarios funcionarioinativo = funcionariosService.inativarFuncionario(id);
        return ResponseEntity.ok().body(funcionarioinativo);
    }

    @PatchMapping("alterarfuncionario/{id}")
    public Funcionarios alterarfuncionario (@PathVariable Long id, Funcionarios funcionario) {
        Funcionarios funcionarioalterado = funcionariosService.alterarFuncionario(id, funcionario);
        return new ResponseEntity<>(funcionarioalterado, HttpStatus.OK).getBody();
    }

    @GetMapping("deletarfuncionario/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        funcionariosService.deletarfuncionario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
