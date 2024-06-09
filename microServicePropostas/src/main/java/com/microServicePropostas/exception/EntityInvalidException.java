package com.microServicePropostas.exception;

public class EntityInvalidException extends RuntimeException {
    public EntityInvalidException(String message) {
        super(message);
    }
}
