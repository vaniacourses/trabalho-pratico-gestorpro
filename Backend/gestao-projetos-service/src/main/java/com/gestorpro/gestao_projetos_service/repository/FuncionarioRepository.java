package com.gestorpro.gestao_projetos_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gestorpro.gestao_projetos_service.model.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    @Query("SELECT f FROM Funcionario f LEFT JOIN FETCH f.projetos WHERE f.id = :id")
    Optional<Funcionario> findByIdWithProjetos(@Param("id") Integer id);
}
