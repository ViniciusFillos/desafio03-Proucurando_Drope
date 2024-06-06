package com.microServiceVotacao.exceptions;

public class UniqueVoteException extends RuntimeException {

    public UniqueVoteException() {
        super("Cada funcionário só tem direito a um voto!");
    }
}
