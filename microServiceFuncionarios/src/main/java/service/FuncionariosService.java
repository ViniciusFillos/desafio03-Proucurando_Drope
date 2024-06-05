package service;

import entities.Funcionarios;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repositories.FuncionariosRepository;

@Service
@RequiredArgsConstructor
public class FuncionariosService {

    private final FuncionariosRepository funcionariosRepository;

    public Funcionarios save(Funcionarios funcionario) {
        return funcionariosRepository.save(funcionario);
    }
}
