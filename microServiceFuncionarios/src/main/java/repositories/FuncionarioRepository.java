package repositories;

import entities.Funcionarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionarios, Long>{
}
