package com.microServiceFuncionarios.unitTests.mapper;

import com.microServiceFuncionarios.mocks.MockFuncionario;
import entities.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.dto.FuncionarioDto;
import web.dto.mapper.FuncionarioMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonMapperTest {

    MockFuncionario inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockFuncionario();
    }

    @Test
    void transformarFuncionarioEmDto() {
        Funcionario funcionario = inputObject.mockEntity(1);
        FuncionarioDto funcionarioDto = FuncionarioMapper.toDto(funcionario);
        assertEquals(funcionario.getNome(), funcionarioDto.getNome());
        assertEquals(funcionario.getCpf(), funcionarioDto.getCpf());
        assertEquals(funcionario.getDataNascimento(), funcionarioDto.getDataNascimento());
    }
}