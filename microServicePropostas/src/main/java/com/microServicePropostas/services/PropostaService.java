package com.microServicePropostas.services;

import com.microServicePropostas.web.dto.PropostaDto;
import com.microServicePropostas.entities.Proposta;
import com.microServicePropostas.repositories.PropostaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
        prop.setTitulo(proposta.getTitulo());
        prop.setDescricao(proposta.getDescricao());
        return propostaRepository.save(prop);
    }

    public void delete(Long id) {
        propostaRepository.deleteById(id);
    }
}
