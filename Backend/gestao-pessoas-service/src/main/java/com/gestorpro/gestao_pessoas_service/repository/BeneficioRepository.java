package com.gestorpro.gestao_pessoas_service.repository;

import com.gestorpro.gestao_pessoas_service.model.Beneficio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficioRepository extends JpaRepository<Beneficio, Integer> {

    // Método para buscar todos os benefícios de um funcionário específico.
    List<Beneficio> findByFuncionario_IdFuncionario(Integer idFuncionario);

}
