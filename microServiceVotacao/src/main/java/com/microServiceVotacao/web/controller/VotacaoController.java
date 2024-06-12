package com.microServiceVotacao.web.controller;

import com.microServiceVotacao.entities.Votacao;
import com.microServiceVotacao.services.VotacaoService;
import com.microServiceVotacao.web.dto.ResultadoVotacaoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Micro Serviço Votação", description = "Contém operações relacionadas à votação")
@RestController
@RequestMapping("/api/v1/votacao")
@RequiredArgsConstructor
public class VotacaoController {

    private final VotacaoService votacaoService;


    @GetMapping
    @Operation(summary = "Buscar todas as votações", description = "Buscar todas as votações registradas",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode =  "200",
                            content = {
                                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Votacao.class)))
                            }),
                    @ApiResponse(description = "Erro Interno", responseCode =  "500", content = @Content)
            })
    public ResponseEntity<List<Votacao>> getAll() {
        return new ResponseEntity<>(votacaoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma votação pelo ID", description = "Buscar uma votação pelo ID especificado",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode =  "200", content = @Content(schema = @Schema(implementation = Votacao.class))),
                    @ApiResponse(description = "Requisição Inválida", responseCode =  "400", content = @Content),
                    @ApiResponse(description = "Não Encontrado", responseCode =  "404", content = @Content),
                    @ApiResponse(description = "Erro Interno", responseCode =  "500", content = @Content)
            })
    public ResponseEntity<Votacao> getById(@PathVariable Long id) {
        Votacao votacao = votacaoService.findById(id);
        return new ResponseEntity<>(votacao, HttpStatus.OK);
    }

    @PostMapping("/encerrar")
    @Operation(summary = "Encerrar votação", description = "Encerrar uma votação em andamento",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode =  "200", content = @Content(schema = @Schema(implementation = ResultadoVotacaoDto.class))),
                    @ApiResponse(description = "Requisição Inválida", responseCode =  "400", content = @Content),
                    @ApiResponse(description = "Erro Interno", responseCode =  "500", content = @Content)
            })
    public ResponseEntity<ResultadoVotacaoDto> encerrarVotacao() {
        ResultadoVotacaoDto resultado = votacaoService.encerrar();
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    @PostMapping("/iniciar/{idProposta}")
    @Operation(summary = "Setar votação ativa/ suporte msPropostas", description = "Mudar o status de votação para ativo e setar o id da Proposta em votação",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode =  "200"),
                    @ApiResponse(description = "Requisição Inválida", responseCode =  "400", content = @Content),
                    @ApiResponse(description = "Erro Interno", responseCode =  "500", content = @Content)
            })
    public void iniciarVotacao(@PathVariable Long idProposta) {
        votacaoService.iniciarVotacao(idProposta);
    }
}
