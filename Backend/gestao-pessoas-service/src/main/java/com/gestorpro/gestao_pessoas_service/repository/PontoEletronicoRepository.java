package com.gestorpro.gestao_pessoas_service.repository;

import com.gestorpro.gestao_pessoas_service.model.PontoEletronico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PontoEletronicoRepository extends JpaRepository<PontoEletronico, Integer> {

    // Método para buscar todos os registos de ponto de um funcionário específico.
    List<PontoEletronico> findByFuncionario_IdFuncionario(Integer idFuncionario);

}
