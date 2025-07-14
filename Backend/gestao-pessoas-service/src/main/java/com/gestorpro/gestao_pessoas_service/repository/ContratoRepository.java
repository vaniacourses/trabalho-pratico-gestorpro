package com.gestorpro.gestao_pessoas_service.repository;

import com.gestorpro.gestao_pessoas_service.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Integer> {

    // Método para buscar todos os contratos de um funcionário específico.
    List<Contrato> findByFuncionarioIdFuncionario(Integer idFuncionario);

}