package com.gestorpro.financeiro_service.despesa.model.entity;

import java.math.BigDecimal;

import com.gestorpro.financeiro_service.despesa.model.enums.DespesaTipo;
import com.gestorpro.financeiro_service.despesa.model.enums.DespesaStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "solicitacoes_de_despesa")
public class Despesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_func", nullable = false)
    private Long idFunc;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private String descricao;

    @Column(name = "motivo_rejeicao", nullable = true)
    private String motivoRejeicao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DespesaStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DespesaTipo tipo;


    public Despesa(Long idFunc, BigDecimal valor, String descricao, DespesaTipo tipo) {
        this.idFunc = idFunc;
        this.valor = valor;
        this.descricao = descricao;
        this.tipo = tipo;
    }

    public Despesa() {}

    public void rejeitar() {
        this.status = DespesaStatus.REJEITADA;
    }

    public void aprovar() {
        this.status = DespesaStatus.APROVADA;
    }

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

    public void setDescricao(String justificativa) {
        this.descricao = justificativa;
    }

    public String getMotivoRejeicao() {
        return motivoRejeicao;
    }

    public void setMotivoRejeicao(String motivoRejeicao) {
        this.motivoRejeicao = motivoRejeicao;
    }
    
    public DespesaStatus getStatus() {
        return status;
    }

    public void setStatus(DespesaStatus status) {
        this.status = status;
    }

    public DespesaTipo getTipo() {
        return tipo;
    }

    public void setTipo(DespesaTipo tipo) {
        this.tipo = tipo;
    }
}
