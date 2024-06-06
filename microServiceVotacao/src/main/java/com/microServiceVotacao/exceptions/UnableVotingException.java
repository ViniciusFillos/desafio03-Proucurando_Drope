package com.microServiceVotacao.exceptions;

public class UnableVotingException extends RuntimeException {
    public UnableVotingException() {
        super("Essa votação já foi encerrada!");
    }
}
