package com.microServiceFuncionarios.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name= "tb_funcionarios")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotEmpty
    @Size(min = 11, max = 11)
    @Pattern(regexp = "\\d{11}")
    @Column(nullable = false, unique = true)
    private String cpf;

    @NotNull
    @Column(nullable = false)
    private LocalDate dataNascimento;

}
