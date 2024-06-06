package com.microServiceFuncionarios.unitTests.services;

import com.microServiceFuncionarios.mocks.MockFuncionario;
import com.microServiceFuncionarios.entities.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.microServiceFuncionarios.repositories.FuncionarioRepository;
import com.microServiceFuncionarios.service.FuncionarioService;
import com.microServiceFuncionarios.web.dto.FuncionarioDto;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {


    MockFuncionario input;

    @InjectMocks
    private FuncionarioService funcionarioService;

    @Mock
    FuncionarioRepository funcionarioRepository;

    @BeforeEach
    void setUpMocks() {
        input = new MockFuncionario();
    }

    @Test
    void salvar_ComDadosValidos_RetornaFuncionario() {
        Funcionario funcionario = input.mockEntity(1);
        FuncionarioDto funcionarioDto = input.mockDto(1);
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);

        var result = funcionarioService.salvar(funcionarioDto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getCpf());
        assertNotNull(result.getDataNascimento());
        assertNotNull(result.getNome());
        assertTrue(result.isAtivo());
        assertEquals("Nome 1", result.getNome());
        assertEquals("11111111111", result.getCpf());
    }

    @Test
    void buscarPorId_ComIdValido_RetornaFuncionario() {
        Funcionario entity = input.mockEntity(1);
        when(funcionarioRepository.findById(anyLong())).thenReturn(Optional.of(entity));

        var result = funcionarioService.buscarPorId(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getCpf());
        assertNotNull(result.getDataNascimento());
        assertNotNull(result.getNome());
        assertTrue(result.isAtivo());
        assertEquals(1L, result.getId());
        assertEquals("Nome 1", result.getNome());
        assertEquals("11111111111", result.getCpf());
    }

    @Test
    void buscarTodos_RetornaLista() {
        List<Funcionario> list = input.mockEntityList();
        when(funcionarioRepository.findAll()).thenReturn(list);

        var funcionarios = funcionarioService.buscarTodos();

        assertNotNull(funcionarios);
        assertEquals(10,funcionarios.size());

        var funcionarioUm = funcionarios.getFirst();
        var funcionarioDez = funcionarios.get(9);

        assertNotNull(funcionarioUm);
        assertNotNull(funcionarioUm.getId());
        assertNotNull(funcionarioUm.getCpf());
        assertNotNull(funcionarioUm.getDataNascimento());
        assertNotNull(funcionarioUm.getNome());
        assertTrue(funcionarioUm.isAtivo());
        assertEquals("Nome 1", funcionarioUm.getNome());
        assertEquals("11111111111", funcionarioUm.getCpf());

        assertNotNull(funcionarioDez);
        assertNotNull(funcionarioDez.getId());
        assertNotNull(funcionarioDez.getCpf());
        assertNotNull(funcionarioDez.getDataNascimento());
        assertNotNull(funcionarioDez.getNome());
        assertTrue(funcionarioDez.isAtivo());
        assertEquals("Nome 10", funcionarioDez.getNome());
        assertEquals("11111111111", funcionarioDez.getCpf());
    }

    @Test
    void inativarFuncionario_ComIdValido_RetornaFuncionario() {
        Funcionario entity = input.mockEntity(1);
        when(funcionarioRepository.findById(anyLong())).thenReturn(Optional.of(entity));

        var result = funcionarioService.inativarFuncionario(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getCpf());
        assertNotNull(result.getDataNascimento());
        assertNotNull(result.getNome());
        assertFalse(result.isAtivo());
        assertEquals(1L, result.getId());
        assertEquals("Nome 1", result.getNome());
        assertEquals("11111111111", result.getCpf());
    }

    @Test
    void alterarFuncionario_ComDadosValidos_RetornaFuncionario() {
        Funcionario funcionario = input.mockEntity(1);
        Funcionario funcionarioAtualizado = input.mockEntity(1);
        funcionarioAtualizado.setNome("Nome Atualizado");
        funcionarioAtualizado.setCpf("22222222222");
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));

        var result = funcionarioService.alterarFuncionario(1L, funcionarioAtualizado);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getCpf());
        assertNotNull(result.getDataNascimento());
        assertNotNull(result.getNome());
        assertTrue(result.isAtivo());
        assertEquals(funcionarioAtualizado.getId(), result.getId());
        assertEquals(funcionarioAtualizado.getNome(), result.getNome());
        assertEquals(funcionarioAtualizado.getCpf(), result.getCpf());
    }

    @Test
    void deletarfuncionario_ComIdValido_NaoRetornaException() {
        assertDoesNotThrow(() -> funcionarioService.deletarfuncionario(1L));
    }
}
