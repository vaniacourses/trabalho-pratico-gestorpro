package com.gestorpro.gestao_pessoas_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "Beneficio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Beneficio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_beneficio")
    private Integer idBeneficio;

    @Column(nullable = false, length = 100)
    private String nome;

    // A anotação @Lob indica que este campo pode armazenar textos longos.
    @Lob
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal valor;

    // --- RELACIONAMENTO COM FUNCIONÁRIO ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Funcionario funcionario;
}
