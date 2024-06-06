package com.microServiceVotacao.exceptions;

public class InvalidIdFuncionarioException extends RuntimeException {
    public InvalidIdFuncionarioException() {
        super("Id de funcionário inválido!");
    }
}
