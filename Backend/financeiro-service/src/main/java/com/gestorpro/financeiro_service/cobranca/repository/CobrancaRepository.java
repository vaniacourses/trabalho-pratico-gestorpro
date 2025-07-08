package com.gestorpro.financeiro_service.cobranca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestorpro.financeiro_service.cobranca.model.entity.Cobranca;

import java.util.List;

@Repository
public interface CobrancaRepository extends JpaRepository<Cobranca, Integer> {
    List<Cobranca> findByProjeto(String projeto);
}
