package com.microServiceVotacao.web.dto;

import lombok.*;

import java.time.LocalDate;

@Data
public class FuncionarioDto {

    String nome;
    String cpf;
    LocalDate dataNascimento;
}
