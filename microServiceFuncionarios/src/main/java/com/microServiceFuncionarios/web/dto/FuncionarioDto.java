package web.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FuncionarioDto {

    String nome;
    String cpf;
    LocalDate dataNascimento;
}
