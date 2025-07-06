package com.gestorpro.gestao_pessoas_service.repository;

import com.gestorpro.gestao_pessoas_service.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    // JpaRepository<TipoDaEntidade, TipoDaChavePrimaria>

    // O Spring Data JPA já dá pra gnt
    // save(funcionario), findById(id), findAll(), deleteById(id)

    // Você também pode criar métodos de busca personalizados.
    // O Spring entende o nome do método e cria a query automaticamente!
    // Exemplo: Buscar todos os funcionários de um determinado departamento.
    List<Funcionario> findByDepartamento(String departamento);
}
