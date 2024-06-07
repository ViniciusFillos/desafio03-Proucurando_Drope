package com.microServicePropostas.exception;

public class VotoUnicoException extends RuntimeException {
    public VotoUnicoException() {
        super("Cada funcionário tem direito de apenas um voto!");
    }
}
