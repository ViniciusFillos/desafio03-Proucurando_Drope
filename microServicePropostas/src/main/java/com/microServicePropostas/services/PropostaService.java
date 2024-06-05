package com.microServicePropostas.services;

import com.microServicePropostas.entities.Proposta;
import com.microServicePropostas.repositories.PropostaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropostaService {

    private final PropostaRepository propostaRepository;

    public Proposta save(Proposta proposta) {
        return propostaRepository.save(proposta);
    }
    public List<Proposta> findAll() {
        return propostaRepository.findAll();
    }
    public Proposta findById(Long id) {
        return propostaRepository.findById(id).get();
    }

    public Proposta update(Long id,Proposta proposta) {
        Proposta prop = findById(id);
        prop.setDescricao(proposta.getDescricao());
        prop.setTitulo(proposta.getTitulo());
        propostaRepository.save(prop);
        return prop;
    }

    public void delete(Long id) {
        propostaRepository.deleteById(id);
    }
}
