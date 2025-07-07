package com.gestorpro.gestao_pessoas_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "FolgaFeriasSolicitacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FolgaFeriasSolicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitacao")
    private Integer idSolicitacao;

    // Mapeia o Enum para uma string no banco de dados.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSolicitacao tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSolicitacao status = StatusSolicitacao.Pendente; // Valor padrão

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    // --- RELACIONAMENTO COM FUNCIONÁRIO ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Funcionario funcionario;
}
