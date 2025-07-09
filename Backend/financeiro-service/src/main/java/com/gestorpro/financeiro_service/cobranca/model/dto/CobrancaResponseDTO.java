package com.gestorpro.financeiro_service.cobranca.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.gestorpro.financeiro_service.cobranca.model.enums.CobrancaStatus;

public class CobrancaResponseDTO {
    private Long id;
    private Long idGestor;
    private String projeto;
    private String descricao;
    private BigDecimal valor;
    private LocalDate dataVencimento;
    private LocalDate dataEmissao;
    private CobrancaStatus status;

    public CobrancaResponseDTO(Long id, Long idGestor, String projeto, String descricao, BigDecimal valor, LocalDate dataEmissao, LocalDate dataVencimento, CobrancaStatus status) {
        this.id = id;
        this.idGestor = idGestor;
        this.projeto = projeto;
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

    public Long getIdGestor() {
        return idGestor;
    }

    public void setIdGestor(Long idGestor) {
        this.idGestor = idGestor;
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

    public CobrancaStatus getStatus() {
        return status;
    }

    public void setStatus(CobrancaStatus status) {
        this.status = status;
    }
}
