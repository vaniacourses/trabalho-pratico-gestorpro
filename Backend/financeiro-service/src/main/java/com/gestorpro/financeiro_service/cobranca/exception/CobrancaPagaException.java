package com.gestorpro.financeiro_service.cobranca.exception;

public class CobrancaPagaException extends RuntimeException {
    public CobrancaPagaException(int id) {
        super("Cobrança com id " + id + " já está paga.");
    }
}