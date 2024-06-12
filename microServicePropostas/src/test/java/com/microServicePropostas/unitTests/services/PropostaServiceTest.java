package com.microServicePropostas.unitTests.services;

import java.util.List;
import java.util.Optional;
import com.microServicePropostas.entities.Proposta;
import com.microServicePropostas.exception.EntityNotFoundException;
import com.microServicePropostas.exception.EntityInvalidException;
import com.microServicePropostas.mocks.MockProposta;
import com.microServicePropostas.repositories.PropostaRepository;
import com.microServicePropostas.services.PropostaService;
import com.microServicePropostas.web.dto.PropostaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
class PropostaServiceTest {

    MockProposta input;

    @Mock
    PropostaRepository propostaRepository;

    @InjectMocks
    private PropostaService propostaService;

    @BeforeEach
    void setUpMocks() {
        input = new MockProposta();
    }

    @Test
    void salvar_ComDadosValidos_RetornarProposta(){
        Proposta proposta = input.mockEntity(1);
        when(propostaRepository.save(any(Proposta.class))).thenReturn(proposta);

        var result = propostaService.save(proposta);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getDescricao());
        assertNotNull(result.getTitulo());
        assertEquals("Titulo do proposta 1", result.getTitulo());
        assertEquals("Descricao do proposta 1", result.getDescricao());

    }

    @Test
    void salvar_ComDadosNulos_RetornarEntityNullException(){
        Proposta proposta = input.mockEntity();
        when(propostaRepository.save(any())).thenThrow(EntityInvalidException.class);

        assertThatThrownBy(() -> propostaService.save(proposta)).isInstanceOf(EntityInvalidException.class);
    }

    @Test
    void buscarPorId_ComIdValido_RetornarProposta(){
        Proposta proposta = input.mockEntity(1);
        when(propostaRepository.findById(anyLong())).thenReturn(Optional.of(proposta));

        var result = propostaService.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getDescricao());
        assertNotNull(result.getTitulo());
        assertEquals(1L, result.getId());
        assertEquals("Titulo do proposta 1", result.getTitulo());
        assertEquals("Descricao do proposta 1", result.getDescricao());
    }

    @Test
    void buscarPorId_ComIdInvalido_RetornarEntityNotFoundException(){
        when(propostaRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> propostaService.findById(0L)).isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    void buscarTodos_RetornarLista(){
        List<Proposta> propostas = input.mockEntityList();
        when(propostaRepository.findAll()).thenReturn(propostas);

        var listPropostas = propostaService.findAll();

        assertNotNull(listPropostas);
        assertEquals(10, listPropostas.size());
        var propostaUm = listPropostas.getFirst();
        var propostaDez = listPropostas.get(9);

        assertNotNull(propostaUm);
        assertNotNull(propostaUm.getId());
        assertNotNull(propostaUm.getDescricao());
        assertNotNull(propostaUm.getTitulo());
        assertEquals("Titulo do proposta 1", propostaUm.getTitulo());
        assertEquals("Descricao do proposta 1", propostaUm.getDescricao());

        assertNotNull(propostaDez);
        assertNotNull(propostaDez.getId());
        assertNotNull(propostaDez.getDescricao());
        assertNotNull(propostaDez.getTitulo());
        assertEquals("Titulo do proposta 10", propostaDez.getTitulo());
        assertEquals("Descricao do proposta 10", propostaDez.getDescricao());
    }

    @Test
    void alterarProposta_ComDadosValidos_RetornaProposta() {
        Proposta proposta = input.mockEntity(1);
        PropostaDto propostaAlterada = input.mockDto(1);
        propostaAlterada.setTitulo("Titulo do proposta Atualizado");
        propostaAlterada.setDescricao("Descricao do proposta Atualizada");
        when(propostaRepository.findById(1L)).thenReturn(Optional.of(proposta));

        var result = propostaService.update(1L, propostaAlterada);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getDescricao());
        assertNotNull(result.getTitulo());
        assertEquals(propostaAlterada.getTitulo(), result.getTitulo());
        assertEquals(propostaAlterada.getDescricao(), result.getDescricao());
    }

    @Test
    void deletarProposta_ComIdValido_NaoRetornaNada() {
        Proposta proposta = input.mockEntity();
        when(propostaRepository.findById(anyLong())).thenReturn(Optional.of(proposta));

        assertDoesNotThrow(() -> propostaService.delete(1L));
    }
}
