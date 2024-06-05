package service;

import entities.Funcionario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import repositories.FuncionarioRepository;
import web.dto.FuncionarioDto;
import web.dto.mapper.FuncionarioMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public Funcionario salvar(FuncionarioDto funcionario) {
        var entity = FuncionarioMapper.toFuncionario(funcionario);
        entity.setAtivo(true);
        return funcionarioRepository.save(entity);
    }

    public Funcionario buscarPorId(Long id) {
        return funcionarioRepository.findById(id).orElseThrow();
    }

    public List<Funcionario> buscarTodos() {
        return funcionarioRepository.findAll();
    }

    public Funcionario inativarFuncionario(Long id) {
        Funcionario funcionario = buscarPorId(id);
        if (funcionario.isAtivo()) {
            funcionario.setAtivo(false);
        }
        return funcionario;
    }

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
        funcionarioRepository.deleteById(id);
    }
}
