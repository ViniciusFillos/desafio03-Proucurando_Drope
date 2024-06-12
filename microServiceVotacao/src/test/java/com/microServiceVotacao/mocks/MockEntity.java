package com.microServiceVotacao.mocks;

import com.microServiceVotacao.entities.Votacao;

import java.util.ArrayList;
import java.util.List;

public class MockEntity {

    public Votacao mockVotacao(){
        return mockVotacao(0);
    }

    public Votacao mockVotacao(Integer num) {
        Votacao votacao = new Votacao();
        votacao.setId(num.longValue());
        votacao.setIdProposta(1L);
        votacao.setVotosPositivos(2);
        votacao.setVotosContras(1);
        return votacao;
    }

    public List<Votacao> mockVotacaoList() {
        List<Votacao> votacoes = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            votacoes.add(mockVotacao(i));
        }
        return votacoes;
    }
}
