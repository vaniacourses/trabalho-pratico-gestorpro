package com.gestorpro.gestao_pessoas_service.repository;

import com.gestorpro.gestao_pessoas_service.model.Salario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalarioRepository extends JpaRepository<Salario, Integer> {

    // Busca o histórico de salários de um funcionário, ordenado pela data mais recente.
    List<Salario> findByFuncionarioIdFuncionarioOrderByDataPagamentoDesc(Integer idFuncionario);
}