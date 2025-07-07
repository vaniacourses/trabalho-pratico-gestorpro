package com.gestorpro.gestao_pessoas_service.repository;

import com.gestorpro.gestao_pessoas_service.model.Salario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalarioRepository extends JpaRepository<Salario, Integer> {

    // Método personalizado para buscar todos os registos de salário de um funcionário específico.
    List<Salario> findByFuncionario_IdFuncionario(Integer idFuncionario);

}
