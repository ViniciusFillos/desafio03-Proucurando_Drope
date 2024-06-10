package com.microServiceFuncionarios.web.controller;

import com.microServiceFuncionarios.entities.Funcionario;
import com.microServiceFuncionarios.web.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.microServiceFuncionarios.service.FuncionarioService;
import com.microServiceFuncionarios.web.dto.FuncionarioDto;

import java.util.List;

@Tag(name = "Micro Serviço Funcionários", description = "Contém todas as operações relativas aos recursos para criar, alterar, visualizar e deletar funcionários")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @Transactional
    @PostMapping
    @Operation(summary = "Criar um Funcionário", description = "Criar um Funcionário passando um JSON",
            responses = {
                    @ApiResponse(description = "Criado com Sucesso", responseCode =  "200", content = @Content(schema = @Schema(implementation = Funcionario.class))),
                    @ApiResponse(description = "CPF já cadastrado", responseCode =  "409", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(description = "Erro Interno", responseCode =  "500", content = @Content)
            })
    public ResponseEntity<Funcionario> salvar (@RequestBody @Valid FuncionarioDto funcionario){
        Funcionario funcionarioCriado = funcionarioService.salvar(funcionario);
        return new ResponseEntity<>(funcionarioCriado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um Funcionário", description = "Buscar um Funcionário pelo id",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode =  "200", content = @Content(schema = @Schema(implementation = Funcionario.class))),
                    @ApiResponse(description = "Requisição Inválida", responseCode =  "400", content = @Content),
                    @ApiResponse(description = "Não Encontrado", responseCode =  "404", content = @Content),
                    @ApiResponse(description = "Erro Interno", responseCode =  "500", content = @Content)
            })
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable Long id) {
        Funcionario funcionario = funcionarioService.buscarPorId(id);
        return new ResponseEntity<>(funcionario, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Buscar todos os Funcionários", description = "Buscar todos os Funcionários",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode =  "200",
                            content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Funcionario.class)))
                            }),
                    @ApiResponse(description = "Não Encontrado", responseCode =  "404", content = @Content),
                    @ApiResponse(description = "Erro Interno", responseCode =  "500", content = @Content)
            })
    public ResponseEntity<List<Funcionario>> buscarTodos() {
        List<Funcionario> todosFuncionarios = funcionarioService.buscarTodos();
        return new ResponseEntity<>(todosFuncionarios, HttpStatus.OK);
    }

    @PatchMapping("inativarfuncionario/{id}")
    @Operation(summary = "Inativar um Funcionário", description = "Inativar um Funcionário pelo id",
            responses = {
                    @ApiResponse(description = "Atualizado", responseCode =  "200", content = @Content(schema = @Schema(implementation = Funcionario.class))),
                    @ApiResponse(description = "Requisição Inválida", responseCode =  "400", content = @Content),
                    @ApiResponse(description = "Não Encontrado", responseCode =  "404", content = @Content),
                    @ApiResponse(description = "Erro Interno", responseCode =  "500", content = @Content)
            })
    public ResponseEntity<Funcionario> inativarFuncionario(@PathVariable Long id) {
        Funcionario funcionarioinativo = funcionarioService.inativarFuncionario(id);
        return ResponseEntity.ok().body(funcionarioinativo);
    }

    @PutMapping("alterar/{id}")
    @Operation(summary = "Alterar um Funcionário", description = "Alterar um Funcionário passando um JSON",
            responses = {
                    @ApiResponse(description = "Atualizado", responseCode =  "200", content = @Content(schema = @Schema(implementation = Funcionario.class))),
                    @ApiResponse(description = "Requisição Inválida", responseCode =  "400", content = @Content),
                    @ApiResponse(description = "Não Encontrado", responseCode =  "404", content = @Content),
                    @ApiResponse(description = "Erro Interno", responseCode =  "500", content = @Content)
            })
    public Funcionario alterarfuncionario (@PathVariable Long id, @RequestBody Funcionario funcionario) {
        Funcionario funcionarioalterado = funcionarioService.alterarFuncionario(id, funcionario);
        return new ResponseEntity<>(funcionarioalterado, HttpStatus.OK).getBody();
    }

    @DeleteMapping("deletar/{id}")
    @Operation(summary = "Deletar um Funcionário", description = "Deletar um Funcionário por id",
            responses = {
                    @ApiResponse(description = "Sem Conteúdo", responseCode =  "204", content = @Content),
                    @ApiResponse(description = "Requisição Inválida", responseCode =  "400", content = @Content),
                    @ApiResponse(description = "Não Encontrado", responseCode =  "404", content = @Content),
                    @ApiResponse(description = "Erro Interno", responseCode =  "500", content = @Content)
            })
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        funcionarioService.deletarfuncionario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
