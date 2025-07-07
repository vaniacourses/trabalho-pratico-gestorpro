package com.gestorpro.gestao_pessoas_service.repository;

import com.gestorpro.gestao_pessoas_service.model.FolgaFeriasSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolgaFeriasSolicitacaoRepository extends JpaRepository<FolgaFeriasSolicitacao, Integer> {

    // Método para buscar todas as solicitações de um funcionário específico.
    List<FolgaFeriasSolicitacao> findByFuncionario_IdFuncionario(Integer idFuncionario);

}
