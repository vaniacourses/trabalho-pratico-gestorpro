package com.gestorpro.financeiro_service.cobranca.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.gestorpro.financeiro_service.cobranca.model.enums.CobrancaStatus;

@Entity
@Table(name = "cobrancas")
public class Cobranca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_gestor", nullable = false)
    private Long idGestor;

    @Column(nullable = false)
    private String projeto;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Column(name = "data_emissao", nullable = false)
    private LocalDate dataEmissao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CobrancaStatus status;

    public Cobranca(Long idGestor, String projeto, String descricao, BigDecimal valor, LocalDate dataVencimento) {
        this.idGestor = idGestor;
        this.projeto = projeto;
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
    }

    public Cobranca() {}

    public void receber() {
        this.status = CobrancaStatus.PAGA;
    }

    public void cancelar() {
        this.status = CobrancaStatus.CANCELADA;
    }

    public Long getId() {
        return id;
    }

    public Long getIdGestor() {
        return idGestor;
    }

    public void setIdGestor(Long idGestor) {
        this.idGestor = idGestor;
    }

    public String getProjeto() {
        return projeto;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public CobrancaStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProjeto(String projeto) {
        this.projeto = projeto;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public void setStatus(CobrancaStatus status) {
        this.status = status;
    }
}