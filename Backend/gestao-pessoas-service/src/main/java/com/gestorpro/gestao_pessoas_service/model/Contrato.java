package com.gestorpro.gestao_pessoas_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Contrato")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contrato")
    private Integer idContrato;

    @Column(nullable = false, length = 50)
    private String tipo;

    private Integer jornada;

    // --- RELACIONAMENTO COM FUNCIONÁRIO ---
    // Muitos Contratos podem pertencer a Um Funcionário.
    @ManyToOne(fetch = FetchType.LAZY)
    // Define a coluna que fará a junção. O nome deve ser igual ao da sua tabela SQL.
    @JoinColumn(name = "id_funcionario", nullable = false)
    // Evita problemas de serialização em loop (quando um contrato mostra um funcionário, que mostra seus contratos, etc.)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    private Funcionario funcionario;
}
