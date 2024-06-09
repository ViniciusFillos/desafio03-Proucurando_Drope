package com.microServicePropostas.validacao;

import com.microServicePropostas.entities.Proposta;
import com.microServicePropostas.exception.EntityInvalidException;
import com.microServicePropostas.web.dto.PropostaDto;
import com.microServicePropostas.web.dto.VotoDto;

public class Validar {

    public static void validarProposta(Proposta proposta) {
        if (proposta == null || proposta.getTitulo() == null || proposta.getDescricao() == null)
            throw new EntityInvalidException("Os campos não devem ser nulos!");
        if (proposta.getTitulo().isEmpty() || proposta.getDescricao().isEmpty())
            throw new EntityInvalidException("Os campos não devem ser vazios!");
    }

    public static void validarProposta(PropostaDto proposta) {
        if (proposta == null || proposta.getTitulo() == null || proposta.getDescricao() == null)
            throw new EntityInvalidException("Os campos não devem ser nulos!");
        if (proposta.getTitulo().isEmpty() || proposta.getDescricao().isEmpty())
            throw new EntityInvalidException("Os campos não devem ser vazios!");
    }

    public static void validarVoto(VotoDto voto) {
        if (voto == null || voto.getIdFuncionario() == null || voto.getAprovo() == null)
            throw new EntityInvalidException("Os campos não devem ser nulos!");
    }
}
