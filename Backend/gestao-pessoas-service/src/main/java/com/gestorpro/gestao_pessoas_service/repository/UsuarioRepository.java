package com.gestorpro.gestao_pessoas_service.repository;

import com.gestorpro.gestao_pessoas_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<User, Integer> {

    // Método essencial para o Spring Security encontrar um usuário pelo seu nome de usuário (email).
    Optional<User> findByEmail(String email);
}