package service;

import entities.Funcionarios;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import repositories.FuncionariosRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionariosService {

    private final FuncionariosRepository funcionariosRepository;

    public Funcionarios salvar(Funcionarios funcionario) {
        return funcionariosRepository.save(funcionario);
    }

    public Funcionarios buscarPorId(Long id) {
        return funcionariosRepository.findById(id).orElseThrow();
    }

    public List<Funcionarios> buscarTodos() {
        List<Funcionarios> todosfuncionarios = funcionariosRepository.findAll();
        return todosfuncionarios;
    }

    public Funcionarios inativarFuncionario(Long id) {
        Funcionarios funcionario = buscarPorId(id);
        if(funcionario.isAtivo()){
            funcionario.setAtivo(false);
        }
    return funcionario;
    }

    public  Funcionarios alterarFuncionario(Long id, Funcionarios funcionario) {
        Funcionarios funcionarioatualizado = buscarPorId(id);
        funcionario.setNome(funcionario.getNome());
        funcionario.setCpf(funcionario.getCpf());
        funcionario.setDataNascimento(funcionario.getDataNascimento());
        funcionariosRepository.save(funcionarioatualizado);
        return funcionarioatualizado;
    }

    public void deletarfuncionario(Long id) {
        funcionariosRepository.deleteById(id);
    }
}
