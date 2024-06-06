package com.microServiceFuncionarios.exceptions;


public class CpfUniqueViolationException extends RuntimeException {
    public CpfUniqueViolationException(String messagem) {
        super(messagem);
    }
}
