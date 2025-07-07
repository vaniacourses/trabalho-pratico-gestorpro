package com.gestorpro.gestao_pessoas_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Salario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_salario")
    private Integer idSalario;

    // Usar BigDecimal é a melhor prática para valores monetários em Java.
    // A anotação @Column define a precisão e escala para corresponder ao seu SQL.
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal valor;

    @Column(name = "data_pagamento", nullable = false)
    private LocalDate dataPagamento;

    // --- RELACIONAMENTO COM FUNCIONÁRIO ---
    // Muitos Salarios podem pertencer a Um Funcionário.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Funcionario funcionario;
}
