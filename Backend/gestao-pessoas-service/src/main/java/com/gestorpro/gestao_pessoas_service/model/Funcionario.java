package com.gestorpro.gestao_pessoas_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Funcionario")
@Data
@NoArgsConstructor 
@AllArgsConstructor 
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionario")
    private Integer idFuncionario;

    @Column(name = "nome", nullable = false, length = 100) 
    private String nome;

    @Column(name = "cargo", nullable = false, length = 50)
    private String cargo;

    @Column(name = "departamento", length = 50)
    private String departamento;

    @Column(name = "data_admissao")
    private LocalDate data_admissao;

    @Column(name = "nivel", length = 30)
    private String nivel;

    @Column(name = "email", length = 40)
    private String email;

    @Column(name = "telefone", length = 20)
    private String telefone;
}
