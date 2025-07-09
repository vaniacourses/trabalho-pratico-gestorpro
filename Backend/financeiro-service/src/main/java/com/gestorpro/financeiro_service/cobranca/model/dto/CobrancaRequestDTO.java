package com.gestorpro.financeiro_service.cobranca.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CobrancaRequestDTO {
    private String emailDevedor;
    private String telefone;
    private String descricao;
    private BigDecimal valor;
    private LocalDate dataVencimento;

    public CobrancaRequestDTO(String emailDevedor, String telefone, String descricao, BigDecimal valor, LocalDate dataVencimento) {
        this.emailDevedor = emailDevedor;
        this.telefone = telefone;
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
    }

    public CobrancaRequestDTO() {}

    public String getEmailDevedor() {
        return emailDevedor;
    }

    public void setEmailDevedor(String emailDevedor) {
        this.emailDevedor = emailDevedor;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}
