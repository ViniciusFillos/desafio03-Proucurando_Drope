package com.microServiceFuncionarios.service;

import com.microServiceFuncionarios.entities.Funcionario;
import com.microServiceFuncionarios.exceptions.EntityNotFoundException;
import com.microServiceFuncionarios.exceptions.UnableException;
import com.microServiceFuncionarios.exceptions.CpfUniqueViolationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.microServiceFuncionarios.repositories.FuncionarioRepository;
import com.microServiceFuncionarios.web.dto.FuncionarioDto;
import com.microServiceFuncionarios.web.dto.mapper.FuncionarioMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public Funcionario salvar(FuncionarioDto funcionario) {
        try {
            var entity = FuncionarioMapper.toFuncionario(funcionario);
            entity.setAtivo(true);
            return funcionarioRepository.save(entity);
        } catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException("Funcionário com um CPF já cadastrado");
        } catch (RuntimeException ex) {
            throw new UnableException("Dados inválidos");
        }
    }

    public Funcionario buscarPorId(Long id) {
        return funcionarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Funcionário id=%s não encontrado", id)));
    }

    public List<Funcionario> buscarTodos() {
        return funcionarioRepository.findAll();
    }

    public Funcionario inativarFuncionario(Long id) {
        Funcionario funcionario = buscarPorId(id);
        if (!funcionario.isAtivo()) {
            throw new UnableException("O funcionários já esta inativado");
        }
        else {
            funcionario.setAtivo(false);
        }
        funcionarioRepository.save(funcionario);
        return funcionario;
    }

    @Transactional
    public Funcionario alterarFuncionario(Long id, Funcionario funcionario) {
        Funcionario funcionarioAtualizado = buscarPorId(id);
        funcionarioAtualizado.setNome(funcionario.getNome());
        funcionarioAtualizado.setCpf(funcionario.getCpf());
        funcionarioAtualizado.setDataNascimento(funcionario.getDataNascimento());
        funcionarioAtualizado.setAtivo(funcionario.isAtivo());
        funcionarioRepository.save(funcionarioAtualizado);
        return funcionarioAtualizado;
    }

    public void deletarfuncionario(Long id) {
        buscarPorId(id);
        funcionarioRepository.deleteById(id);
    }

}
