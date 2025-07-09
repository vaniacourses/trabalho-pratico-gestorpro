package com.gestorpro.financeiro_service.cobranca.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CobrancaRequestDTO {
    private Long idGestor;
    private String projeto;
    private String descricao;
    private BigDecimal valor;
    private LocalDate dataVencimento;

    public CobrancaRequestDTO(Long idGestor, String projeto, String descricao, BigDecimal valor, LocalDate dataVencimento) {
        this.idGestor = idGestor;
        this.projeto = projeto;
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
    }

    public CobrancaRequestDTO() {}

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

    public LocalDate getDataVencimento() {
        return dataVencimento;
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
}
