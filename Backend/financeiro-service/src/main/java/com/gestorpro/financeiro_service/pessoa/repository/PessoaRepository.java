package com.gestorpro.financeiro_service.pessoa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestorpro.financeiro_service.pessoa.model.entity.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, String> {
    Pessoa findByEmail(String email);
}