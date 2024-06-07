package com.microServicePropostas.exception;

public class VotacaoAtivaException extends RuntimeException {
    public VotacaoAtivaException() {
        super("No momento outra votação já está ativa! por favor encerre-a!");
    }
}
