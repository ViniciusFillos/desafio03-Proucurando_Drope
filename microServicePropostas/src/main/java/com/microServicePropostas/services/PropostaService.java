package com.microServicePropostas.services;

import com.microServicePropostas.exception.EntityNullException;
import com.microServicePropostas.entities.Proposta;
import com.microServicePropostas.repositories.PropostaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropostaService {

    private final PropostaRepository propostaRepository;

    public Proposta save(Proposta proposta) {
        if(proposta == null){
            throw new EntityNullException(String.format("Os campos não devem ser nulos!"));
        }
        return propostaRepository.save(proposta);
    }

    public List<Proposta> findAll() {
            return propostaRepository.findAll();
    }

    public Proposta findById(Long id) {
        return propostaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Proposta id=%s não encontrado!", id))
        );
    }

    public Proposta update(Long id,Proposta proposta) {
        Proposta prop = findById(id);
        prop.setId(proposta.getId());
        prop.setTitulo(proposta.getTitulo());
        prop.setDescricao(proposta.getDescricao());
        propostaRepository.save(prop);
        return prop;
    }

    public void delete(Long id) {
        propostaRepository.deleteById(id);
    }
}
