package com.gestorpro.gestao_pessoas_service.repository;

import com.gestorpro.gestao_pessoas_service.model.SolicitacaoAfastamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitacaoAfastamentoRepository extends JpaRepository<SolicitacaoAfastamento, Integer> {

    // Método para buscar todas as solicitações de um funcionário específico.
    // O nome do método é traduzido pelo Spring Data JPA em uma consulta SQL.
    List<SolicitacaoAfastamento> findByFuncionarioIdFuncionario(Integer idFuncionario);
}