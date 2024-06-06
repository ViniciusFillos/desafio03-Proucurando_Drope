package com.microServiceVotacao.web.controller;

import com.microServiceVotacao.entities.Votacao;
import com.microServiceVotacao.services.VotacaoService;
import com.microServiceVotacao.web.controller.dto.VotoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/votacao")
@RequiredArgsConstructor
public class VotacaoController {

    private final VotacaoService votacaoService;

    @PatchMapping("/iniciar/{idProposta}")
    public ResponseEntity<Votacao> iniciarVotacao (@PathVariable Long idProposta) {
        Votacao votacao = votacaoService.iniciar(idProposta);
        return new ResponseEntity<>(votacao, HttpStatus.CREATED);
    }

    @PatchMapping("/encerrar/{idVotacao}")
    public ResponseEntity<String> encerrarVotacao (@PathVariable Long idVotacao) {
        Votacao votacao = votacaoService.encerrar(idVotacao);
        return new ResponseEntity<>("Votação "+votacao.getId()+" encerrada", HttpStatus.OK);
    }

    @PatchMapping("/{idVotacao}")
    public ResponseEntity<VotoDto> votar (@PathVariable Long idVotacao ,@RequestBody VotoDto voto) {
        VotoDto response = votacaoService.votar(idVotacao, voto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Votacao>> getAll () {
        return new ResponseEntity<>(votacaoService.findAll(), HttpStatus.OK);
    }
}
