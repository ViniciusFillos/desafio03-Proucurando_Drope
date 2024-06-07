package com.microServicePropostas.web.controller;

import com.microServicePropostas.web.dto.PropostaDto;
import com.microServicePropostas.entities.Proposta;
import com.microServicePropostas.services.PropostaService;
import com.microServicePropostas.web.dto.VotacaoDto;
import com.microServicePropostas.web.dto.VotoDto;
import com.microServicePropostas.web.dto.mapper.PropostaMapper;
import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/propostas")
public class PropostaController {

    private final PropostaService propostaService;

    @GetMapping
    public ResponseEntity<List<Proposta>> getAll() {
        List<Proposta> propostas = propostaService.findAll();
        return new ResponseEntity<>(propostas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proposta> getById(@PathVariable Long id) {
        Proposta proposta = propostaService.findById(id);
        return new ResponseEntity<>(proposta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Proposta> create(@RequestBody PropostaDto proposta) {
        Proposta criado = propostaService.save(PropostaMapper.toProposta(proposta));
        return new ResponseEntity <>(criado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proposta> update(@PathVariable Long id, @RequestBody Proposta proposta) {
        Proposta atualizado = propostaService.update(id ,proposta);
        return new ResponseEntity<>(atualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        propostaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/votacao/{idProposta}")
    public ResponseEntity<VotacaoDto> iniciarVotacao(@PathVariable Long idProposta){
        VotacaoDto dto = propostaService.iniciarVotacao(idProposta);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/votacao/ancerrar")
    public void mudarStatusVotacaoAtivo(){
        propostaService.mudarStatusVotacaoAtivo();
    }

    @PostMapping("/votar")
    public ResponseEntity<VotoDto> votar(@RequestBody VotoDto votoDto){
        VotoDto dto = propostaService.votar(votoDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
