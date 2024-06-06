package com.microServiceVotacao.exceptions;

public class ActiveVotingException extends RuntimeException {
    public ActiveVotingException() {
        super("Outra votação já está ativa no momento!");
    }
}
