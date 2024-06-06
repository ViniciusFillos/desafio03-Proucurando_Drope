package com.microServiceVotacao.exceptions;

public class InvalidIdVotingExcption extends RuntimeException{
    public InvalidIdVotingExcption() {
        super("Id de votação inválido");
    }
}
