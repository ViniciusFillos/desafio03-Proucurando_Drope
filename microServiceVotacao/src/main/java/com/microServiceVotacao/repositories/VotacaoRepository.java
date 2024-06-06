package com.microServiceVotacao.repositories;

import com.microServiceVotacao.entities.Votacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotacaoRepository extends JpaRepository<Votacao, Long> {
}

