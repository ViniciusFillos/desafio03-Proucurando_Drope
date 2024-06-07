package com.microServicePropostas.exception;

public class VotacaoJaAativadaException extends RuntimeException {
    public VotacaoJaAativadaException() {
        super("Já existe uma votação ativa no momento!");
    }
}
