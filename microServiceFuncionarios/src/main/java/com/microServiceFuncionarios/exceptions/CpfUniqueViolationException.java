package com.microServiceFuncionarios.exceptions;


import org.springframework.dao.DataIntegrityViolationException;

public class CpfUniqueViolationException extends DataIntegrityViolationException {
    public CpfUniqueViolationException(String messagem) {
        super(messagem);
    }
}