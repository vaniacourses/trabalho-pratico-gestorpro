package com.gestorpro.gestao_projetos_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "funcionario_projetos",
            joinColumns = @JoinColumn(name = "id_funcionario"),
            inverseJoinColumns = @JoinColumn(name = "id_projeto")
    )
    private Set<Projeto> projetos = new HashSet<>();
}