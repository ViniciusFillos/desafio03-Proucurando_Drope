package com.microServicePropostas.web.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FuncionarioDto {

    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private boolean ativo;
}
