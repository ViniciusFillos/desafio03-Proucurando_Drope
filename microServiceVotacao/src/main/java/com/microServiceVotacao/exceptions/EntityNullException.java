package com.microServiceVotacao.exceptions;

public class EntityNullException extends RuntimeException {
    public EntityNullException() {
        super("Votação está nula e não pode ser salva!");
    }
}
