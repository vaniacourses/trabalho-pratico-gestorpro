package com.gestorpro.financeiro_service.despesa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestorpro.financeiro_service.despesa.model.entity.Despesa;
import com.gestorpro.financeiro_service.despesa.model.enums.DespesaStatus;

import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Integer> {
    List<Despesa> findByStatus(DespesaStatus status);
}
