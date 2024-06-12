package com.microServiceVotacao.exceptions;

public class EntityNullException extends RuntimeException {
    public EntityNullException(String message) {
        super(message);
    }
}
