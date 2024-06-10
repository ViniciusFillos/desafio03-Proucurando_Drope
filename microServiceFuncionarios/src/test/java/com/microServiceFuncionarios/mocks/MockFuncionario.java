package com.microServiceFuncionarios.mocks;

import com.microServiceFuncionarios.entities.Funcionario;

import com.microServiceFuncionarios.web.dto.FuncionarioDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class MockFuncionario {

    public Funcionario mockEntity() {
        return mockEntity(0);
    }

    public FuncionarioDto mockDto() {
        return mockDto(0);
    }

    public List<Funcionario> mockEntityList() {
        List<Funcionario> persons = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            persons.add(mockEntity(i));
        }
        return persons;
    }

    public List<FuncionarioDto> mockDtoList() {
        List<FuncionarioDto> persons = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            persons.add(mockDto(i));
        }
        return persons;
    }

    public Funcionario mockEntity(Integer number) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Nome " + number);
        funcionario.setDataNascimento(LocalDate.now());
        funcionario.setAtivo(true);
        funcionario.setCpf("11111111111");
        funcionario.setId(number.longValue());
        return funcionario;
    }

    public FuncionarioDto mockDto(Integer number) {
        FuncionarioDto funcionarioDto = new FuncionarioDto();
        funcionarioDto.setCpf("11111111111");
        funcionarioDto.setNome("Nome " + number);
        funcionarioDto.setDataNascimento(LocalDate.now());
        return funcionarioDto;
    }
}
