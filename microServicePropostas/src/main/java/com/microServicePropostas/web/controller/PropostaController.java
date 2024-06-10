package com.microServicePropostas.web.controller;

import com.microServicePropostas.web.dto.PropostaDto;
import com.microServicePropostas.entities.Proposta;
import com.microServicePropostas.services.PropostaService;
import com.microServicePropostas.web.dto.VotacaoDto;
import com.microServicePropostas.web.dto.VotoDto;
import com.microServicePropostas.web.dto.mapper.PropostaMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Micro Serviço Propostas", description = "Contém operações relacionadas às propostas e votações em propostas")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/propostas")
public class PropostaController {

    private final PropostaService propostaService;

    @GetMapping
    @Operation(summary = "Buscar todas as propostas", description = "Buscar todas as propostas registradas",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode =  "200",
                            content = {
                                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Proposta.class)))
                            }),
                    @ApiResponse(description = "Não Encontrado", responseCode =  "404", content = @Content),
                    @ApiResponse(description = "Erro Interno", responseCode =  "500", content = @Content)
            })
    public ResponseEntity<List<Proposta>> getAll() {
        List<Proposta> propostas = propostaService.findAll();
        return new ResponseEntity<>(propostas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma proposta pelo ID", description = "Buscar uma proposta pelo ID especificado",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode =  "200", content = @Content(schema = @Schema(implementation = Proposta.class))),
                    @ApiResponse(description = "Requisição Inválida", responseCode =  "400", content = @Content),
                    @ApiResponse(description = "Não Encontrado", responseCode =  "404", content = @Content),
                    @ApiResponse(description = "Erro Interno", responseCode =  "500", content = @Content)
            })
    public ResponseEntity<Proposta> getById(@PathVariable Long id) {
        Proposta proposta = propostaService.findById(id);
        return new ResponseEntity<>(proposta, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Criar uma nova proposta", description = "Criar uma nova proposta com base nos dados fornecidos",
            responses = {
                    @ApiResponse(description = "Criado com Sucesso", responseCode =  "201", content = @Content(schema = @Schema(implementation = Proposta.class))),
                    @ApiResponse(description = "Erro Interno", responseCode =  "500", content = @Content)
            })
    public ResponseEntity<Proposta> create(@RequestBody @Valid PropostaDto proposta) {
        Proposta criado = propostaService.save(PropostaMapper.toProposta(proposta));
        return new ResponseEntity <>(criado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma proposta", description = "Atualizar uma proposta existente com base no ID especificado",
            responses = {
                    @ApiResponse(description = "Atualizado com Sucesso", responseCode =  "200", content = @Content(schema = @Schema(implementation = Proposta.class))),
                    @ApiResponse(description = "Requisição Inválida", responseCode =  "400", content = @Content),
                    @ApiResponse(description = "Não Encontrado", responseCode =  "404", content = @Content),
                    @ApiResponse(description = "Erro Interno", responseCode =  "500", content = @Content)
            })
    public ResponseEntity<Proposta> update(@PathVariable Long id, @RequestBody @Valid PropostaDto proposta) {
        Proposta propostaAtualizada = propostaService.update(id ,proposta);
        return new ResponseEntity<>(propostaAtualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma proposta", description = "Deletar uma proposta com base no ID especificado",
            responses = {
                    @ApiResponse(description = "Sem Conteúdo", responseCode =  "204"),
                    @ApiResponse(description = "Requisição Inválida", responseCode =  "400", content = @Content),
                    @ApiResponse(description = "Não Encontrado", responseCode =  "404", content = @Content),
                    @ApiResponse(description = "Erro Interno", responseCode =  "500", content = @Content)
            })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        propostaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/votacao/{idProposta}")
    @Operation(summary = "Iniciar votação para uma proposta", description = "Iniciar votação para a proposta com o ID especificado",
            responses = {
                    @ApiResponse(description = "Criado com Sucesso", responseCode =  "201", content = @Content(schema = @Schema(implementation = VotacaoDto.class))),
                    @ApiResponse(description = "Requisição Inválida", responseCode =  "400", content = @Content),
                    @ApiResponse(description = "Erro Interno", responseCode =  "500", content = @Content)
            })
    public ResponseEntity<VotacaoDto> iniciarVotacao(@PathVariable Long idProposta, @RequestParam(defaultValue = "1", required = false) Integer limite){
        VotacaoDto dto = propostaService.iniciarVotacao(idProposta, limite);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/votacao/encerrar")
    @Operation(summary = "Encerrar votação", description = "Encerra a votação e retorna o resultado com um JSON",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200"),
                    @ApiResponse(description = "Nenhuma votação ativa", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)
            })
    public void mudarStatusVotacaoAtivo(){
        propostaService.mudarStatusVotacaoAtivo();
    }

    @PostMapping("/votar")
    @Operation(summary = "Votar em uma votação ativa", description = "Votar em uma proposta com votação ativa",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200"),
                    @ApiResponse(description = "Nenhuma votação ativa", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)

            })
    public ResponseEntity<VotoDto> votar(@RequestBody VotoDto votoDto){
        VotoDto dto = propostaService.votar(votoDto);
        propostaService.integrarVoto(votoDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
