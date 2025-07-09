package com.gestorpro.financeiro_service.despesa.model.dto;

import java.math.BigDecimal;

import com.gestorpro.financeiro_service.despesa.model.enums.DespesaStatus;
import com.gestorpro.financeiro_service.despesa.model.enums.DespesaTipo;

public class DespesaResponseDTO {
    private Long id;
    private Long idFunc;
    private BigDecimal valor;
    private String descricao;
    private String motivoRejeicao;
    private DespesaTipo tipo;
    private DespesaStatus status;

    public DespesaResponseDTO(Long id, Long idFunc, BigDecimal valor, String descricao, String motivoRejeicao, DespesaTipo tipo, DespesaStatus status) {
        this.id = id;
        this.idFunc = idFunc;
        this.valor = valor;
        this.descricao = descricao;
        this.motivoRejeicao = motivoRejeicao;
        this.tipo = tipo;
        this.status = status;
    }

    public DespesaResponseDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getMotivoRejeicao() {
        return motivoRejeicao;
    }

    public void setMotivoRejeicao(String motivoRejeicao) {
        this.motivoRejeicao = motivoRejeicao;
    }

    public DespesaTipo getTipo() {
        return tipo;
    }

    public void setTipo(DespesaTipo tipo) {
        this.tipo = tipo;
    }

    public DespesaStatus getStatus() {
        return status;
    }

    public void setStatus(DespesaStatus status) {
        this.status = status;
    }
}
