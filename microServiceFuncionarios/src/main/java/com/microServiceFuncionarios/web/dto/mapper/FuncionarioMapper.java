package com.microServiceFuncionarios.web.dto.mapper;

import com.microServiceFuncionarios.entities.Funcionario;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import com.microServiceFuncionarios.web.dto.FuncionarioDto;

@NoArgsConstructor
public class FuncionarioMapper {

    public static FuncionarioDto toDto(Funcionario funcionario) {
        return new ModelMapper().map(funcionario, FuncionarioDto.class);
    }

    public static Funcionario toFuncionario(FuncionarioDto funcionarioDto) {
        return new ModelMapper().map(funcionarioDto, Funcionario.class);
    }
}
