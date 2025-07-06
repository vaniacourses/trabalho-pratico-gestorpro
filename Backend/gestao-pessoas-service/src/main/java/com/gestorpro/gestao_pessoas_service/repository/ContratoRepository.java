package com.gestorpro.gestao_pessoas_service.repository;

import com.gestorpro.gestao_pessoas_service.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Integer> {

    // O Spring Data JPA criará este método automaticamente para nós!
    // Ele vai buscar todos os contratos que pertencem a um funcionário específico.
    List<Contrato> findByFuncionario_IdFuncionario(Integer idFuncionario);

}
