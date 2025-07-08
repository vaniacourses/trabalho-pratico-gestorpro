package com.gestorpro.financeiro_service.cobranca.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CobrancaRequestDTO {
    private String projeto;
    private String descricao;
    private BigDecimal valor;
    private LocalDate dataVencimento;
    private String emailNotificacao;

    public CobrancaRequestDTO(String projeto, String descricao, BigDecimal valor, LocalDate dataVencimento, String emailNotificacao) {
        this.projeto = projeto;
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.emailNotificacao = emailNotificacao;
    }

    public CobrancaRequestDTO() {}

    public String getProjeto() {
        return projeto;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public String getEmailNotificacao() {
        return emailNotificacao;
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

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public void setEmailNotificacao(String emailNotificacao) {
        this.emailNotificacao = emailNotificacao;
    }
}
