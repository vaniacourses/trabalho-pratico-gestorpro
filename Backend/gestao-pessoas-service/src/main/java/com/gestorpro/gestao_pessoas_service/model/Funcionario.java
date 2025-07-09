package com.gestorpro.gestao_pessoas_service.model;

import java.util.ArrayList;
import java.util.List;

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

    // ... todos os outros atributos de Funcionario ...
    @Column(name = "cargo", nullable = false, length = 50)
    private String cargo;

    @Column(name = "departamento", length = 50)
    private String departamento;

    @Column(name = "data_admissao")
    private LocalDate data_admissao;

    @Column(name = "nivel", length = 30)
    private String nivel;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
        name = "usuario_email",
        referencedColumnName = "email",
        nullable = false,
        unique = true
    )
    private User usuario;

    // 2. INICIALIZE AS LISTAS DIRETAMENTE NA DECLARAÇÃO
    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contrato> contratos = new ArrayList<>();

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Beneficio> beneficios = new ArrayList<>();

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SolicitacaoAfastamento> solicitacoesAfastamento = new ArrayList<>();

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistroPonto> registrosPonto = new ArrayList<>();
 
    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Salario> salarios = new ArrayList<>();
}