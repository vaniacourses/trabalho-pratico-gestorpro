package com.gestorpro.gestao_projetos_service.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "projetos")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_projeto")
    private Long id;

    private String tipo;
    private String cliente;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private String status;

    @ManyToMany(mappedBy = "projetos")
    private Set<Funcionario> funcionarios = new HashSet<>();
}
