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

    @Column(name = "email_notificacao", nullable = false)
    private String emailNotificacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CobrancaStatus status;

    public Cobranca(String projeto, String descricao, BigDecimal valor, LocalDate dataVencimento, String emailNotificacao) {
        this.projeto = projeto;
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.emailNotificacao = emailNotificacao;
    }

    public Cobranca() {}

    public void atualizarStatus(CobrancaStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
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

    public String getEmailNotificacao() {
        return emailNotificacao;
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

    public void setEmailNotificacao(String emailNotificacao) {
        this.emailNotificacao = emailNotificacao;
    }

    public void setStatus(CobrancaStatus status) {
        this.status = status;
    }
}