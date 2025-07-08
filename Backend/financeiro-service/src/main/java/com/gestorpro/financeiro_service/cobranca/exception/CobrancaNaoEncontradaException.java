package com.gestorpro.financeiro_service.cobranca.exception;

public class CobrancaNaoEncontradaException extends RuntimeException {
    public CobrancaNaoEncontradaException(int id) {
        super("Cobrança com id " + id + " não encontrada.");
    }
}
