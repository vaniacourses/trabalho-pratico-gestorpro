package com.gestorpro.financeiro_service.despesa.exception;

public class DespesaProcessadaException extends RuntimeException {
    public DespesaProcessadaException(int id) {
        super("Despesa com ID " + id + " já foi aprovada ou rejeitada.");
    }
}
