package com.gestorpro.gestao_pessoas_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "PontoEletronico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PontoEletronico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ponto")
    private Integer idPonto;

    @Column(name = "data_registro", nullable = false)
    private LocalDate dataRegistro;

    @Column(name = "hora_entrada", nullable = false)
    private LocalTime horaEntrada;

    // A hora de saída pode ser nula se o funcionário ainda não tiver batido o ponto de saída.
    @Column(name = "hora_saida")
    private LocalTime horaSaida;

    // --- RELACIONAMENTO COM FUNCIONÁRIO ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Funcionario funcionario;
}
