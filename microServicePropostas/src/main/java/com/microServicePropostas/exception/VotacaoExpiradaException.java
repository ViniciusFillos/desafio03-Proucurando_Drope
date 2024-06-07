package com.microServicePropostas.exception;

public class VotacaoExpiradaException extends RuntimeException {
    public VotacaoExpiradaException() {
        super("Votacao expirada! por favor encerre-a!");
    }
}
