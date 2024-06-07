package com.microServicePropostas.exception;

public class VotacaoAtivaException extends RuntimeException {
    public VotacaoAtivaException() {
        super("Nenhuma votação esta ativa no momento!");
    }
}
