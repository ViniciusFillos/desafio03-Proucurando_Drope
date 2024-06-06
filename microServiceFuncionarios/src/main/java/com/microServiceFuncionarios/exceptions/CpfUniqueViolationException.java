package com.microServiceFuncionarios.exceptions;


public class CpfUniqueViolationException extends RuntimeException {
    public CpfUniqueViolationException(String message) {
        super(message);
    }
}
