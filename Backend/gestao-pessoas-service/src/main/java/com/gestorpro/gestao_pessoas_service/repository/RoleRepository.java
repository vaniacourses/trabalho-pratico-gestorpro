package com.gestorpro.gestao_pessoas_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestorpro.gestao_pessoas_service.model.Role;
import com.gestorpro.gestao_pessoas_service.model.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
