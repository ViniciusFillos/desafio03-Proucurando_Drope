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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final Logger logger = Logger.getLogger(FuncionarioService.class.getName());

    public Funcionario salvar(FuncionarioDto funcionario) {
        logger.info("Salvando um funcionário!");
        try {
            var entity = FuncionarioMapper.toFuncionario(funcionario);
            return funcionarioRepository.save(entity);
        } catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException("Funcionário com um CPF já cadastrado!");
        } catch (RuntimeException ex) {
            throw new UnableException("Dados inválidos!");
        }
    }

    public Funcionario buscarPorId(Long id) {
        logger.info("Buscando um funcionário!");
        try {
            return funcionarioRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException(String.format("Funcionário id=%s não encontrado!", id)));
        }  catch (MethodArgumentTypeMismatchException ex) {
            throw new UnableException("Dados inválidos!");
        }
    }

    public List<Funcionario> buscarTodos() {
        logger.info("Busacando todos funcionários!");
        return funcionarioRepository.findAll();
    }

    @Transactional
    public Funcionario alterarFuncionario(Long id, Funcionario funcionario) {
        logger.info("Alterando um funcionário!");
        Funcionario funcionarioAtualizado = buscarPorId(id);
        funcionarioAtualizado.setNome(funcionario.getNome());
        funcionarioAtualizado.setCpf(funcionario.getCpf());
        funcionarioAtualizado.setDataNascimento(funcionario.getDataNascimento());
        funcionarioRepository.save(funcionarioAtualizado);
        return funcionarioAtualizado;
    }

    public void deletarfuncionario(Long id) {
        logger.info("Deletando um funcionário!");
        buscarPorId(id);
        funcionarioRepository.deleteById(id);
    }

    public void votacaoEncerrada(String resultado) {
        logger.info("Votação encerrada! " + resultado);
    }
}
