package com.microServicePropostas.exception;

public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException() {
        super("Insira um Id de funcionário válido!");
    }
}
