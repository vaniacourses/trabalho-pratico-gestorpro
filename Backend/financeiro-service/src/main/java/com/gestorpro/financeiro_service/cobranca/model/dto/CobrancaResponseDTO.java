package com.gestorpro.financeiro_service.cobranca.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.gestorpro.financeiro_service.cobranca.model.enums.CobrancaStatus;

public class CobrancaResponseDTO {
    private Long id;
    private String emailDevedor;
    private String telefoneDevedor;
    private String descricao;
    private BigDecimal valor;
    private LocalDate dataVencimento;
    private LocalDate dataEmissao;
    private CobrancaStatus status;

    public CobrancaResponseDTO(Long id, String emailDevedor, String telefoneDevedor, String descricao, BigDecimal valor, LocalDate dataEmissao, LocalDate dataVencimento, CobrancaStatus status) {
        this.id = id;
        this.emailDevedor = emailDevedor;
        this.telefoneDevedor = telefoneDevedor;
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.dataEmissao = dataEmissao;
        this.status = status;
    }

    public CobrancaResponseDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailDevedor() {
        return emailDevedor;
    }

    public void setEmailDevedor(String emailDevedor) {
        this.emailDevedor = emailDevedor;
    }

    public String getTelefoneDevedor() {
        return telefoneDevedor;
    }

    public void setTelefoneDevedor(String telefoneDevedor) {
        this.telefoneDevedor = telefoneDevedor;
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

    public CobrancaStatus getStatus() {
        return status;
    }

    public void setStatus(CobrancaStatus status) {
        this.status = status;
    }
}
