package com.gestorpro.financeiro_service.despesa.exception;

public class DespesaNaoEncontradaException extends RuntimeException {
    public DespesaNaoEncontradaException(int id) {
        super("Despesa com ID " + id + " não encontrada.");
    }
}
