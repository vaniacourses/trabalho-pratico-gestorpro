package com.gestorpro.gestao_pessoas_service.repository;

import com.gestorpro.gestao_pessoas_service.model.RegistroPonto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegistroPontoRepository extends JpaRepository<RegistroPonto, Integer> {

    // Busca todos os registros de um funcionário
    List<RegistroPonto> findByFuncionarioIdFuncionarioOrderByDataDesc(Integer idFuncionario);

    // Busca um registro específico de um funcionário em uma data (útil para registrar a saída)
    Optional<RegistroPonto> findByFuncionarioIdFuncionarioAndData(Integer idFuncionario, LocalDate data);
}