package com.gestorpro.gestao_pessoas_service.repository;

import com.gestorpro.gestao_pessoas_service.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    // Exemplo de método de busca customizado para encontrar funcionários por parte do nome.
    List<Funcionario> findByNomeContainingIgnoreCase(String nome);
}