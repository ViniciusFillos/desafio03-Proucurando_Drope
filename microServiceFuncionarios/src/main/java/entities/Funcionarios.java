package entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name= "tb_funcionarios")
public class Funcionarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column
    private String nome;

    @NotNull
    @Column
    private String cpf;

    @NotNull
    @Column
    private LocalDate dataNascimento;

    @NotNull
    @Column
    private boolean ativo;

}
