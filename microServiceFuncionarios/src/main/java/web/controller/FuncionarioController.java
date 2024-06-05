package web.controller;

import entities.Funcionario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.FuncionarioService;
import web.dto.FuncionarioDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<Funcionario> salvar (@RequestBody FuncionarioDto funcionario){
        Funcionario funcionarioCriado = funcionarioService.salvar(funcionario);
        return new ResponseEntity<>(funcionarioCriado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable Long id) {
        Funcionario funcionario = funcionarioService.buscarPorId(id);
        return new ResponseEntity<>(funcionario, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> buscarTodos() {
        List<Funcionario> todosFuncionarios = funcionarioService.buscarTodos();
        return new ResponseEntity<>(todosFuncionarios, HttpStatus.OK);
    }

    @PatchMapping("inativarfuncionario/{id}")
    public ResponseEntity<Funcionario> inativarFuncionario(@PathVariable  Long id) {
        Funcionario funcionarioinativo = funcionarioService.inativarFuncionario(id);
        return ResponseEntity.ok().body(funcionarioinativo);
    }

    @PatchMapping("alterarfuncionario/{id}")
    public Funcionario alterarfuncionario (@PathVariable Long id, Funcionario funcionario) {
        Funcionario funcionarioalterado = funcionarioService.alterarFuncionario(id, funcionario);
        return new ResponseEntity<>(funcionarioalterado, HttpStatus.OK).getBody();
    }

    @GetMapping("deletarfuncionario/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        funcionarioService.deletarfuncionario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
