package com.gestorpro.financeiro_service.despesa.model.dto;

import com.gestorpro.financeiro_service.despesa.model.enums.DespesaTipo;

import java.math.BigDecimal;

public class DespesaRequestDTO {
    private Long idFunc;
    private BigDecimal valor;
    private String descricao;
    private DespesaTipo tipo;

    public DespesaRequestDTO(Long idFunc, BigDecimal valor, String descricao, DespesaTipo tipo) {
        this.idFunc = idFunc;
        this.valor = valor;
        this.descricao = descricao;
        this.tipo = tipo;
    }

    public DespesaRequestDTO() {}

    public Long getIdFunc() {
        return idFunc;
    }

    public void setIdFunc(Long idFunc) {
        this.idFunc = idFunc;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }  

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public DespesaTipo getTipo() {
        return tipo;
    }

    public void setTipo(DespesaTipo tipo) {
        this.tipo = tipo;
    }
}
