package com.gestorpro.financeiro_service.cobranca.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.gestorpro.financeiro_service.cobranca.model.enums.CobrancaStatus;

public class CobrancaResponseDTO {
    private Long id;
    private String projeto;
    private String descricao;
    private BigDecimal valor;
    private LocalDate dataVencimento;
    private LocalDate dataEmissao;
    private String emailNotificacao;
    private CobrancaStatus status;

    public CobrancaResponseDTO(Long id, String projeto, String descricao, BigDecimal valor, LocalDate dataEmissao, LocalDate dataVencimento, String emailNotificacao, CobrancaStatus status) {
        this.id = id;
        this.projeto = projeto;
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.dataEmissao = dataEmissao;
        this.emailNotificacao = emailNotificacao;
        this.status = status;
    }

    public CobrancaResponseDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjeto() {
        return projeto;
    }

    public void setProjeto(String projeto) {
        this.projeto = projeto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getEmailNotificacao() {
        return emailNotificacao;
    }

    public void setEmailNotificacao(String emailNotificacao) {
        this.emailNotificacao = emailNotificacao;
    }

    public CobrancaStatus getStatus() {
        return status;
    }

    public void setStatus(CobrancaStatus status) {
        this.status = status;
    }
}
