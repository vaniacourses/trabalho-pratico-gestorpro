package com.gestorpro.financeiro_service.despesa.model.dto;

public class DespesaRejectedDTO {
    private String justificativa;

    public DespesaRejectedDTO(String justificativa) {
        this.justificativa = justificativa;
    }

    public DespesaRejectedDTO() {}

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }
}
