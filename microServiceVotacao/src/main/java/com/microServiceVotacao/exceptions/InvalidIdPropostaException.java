package com.microServiceVotacao.exceptions;

public class InvalidIdPropostaException extends RuntimeException {
    public InvalidIdPropostaException() {
        super("Id de propósta inválida!");
    }
}
