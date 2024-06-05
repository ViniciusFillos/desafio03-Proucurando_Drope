package com.microServiceFuncionarios.repositories;

import com.microServiceFuncionarios.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{
}
