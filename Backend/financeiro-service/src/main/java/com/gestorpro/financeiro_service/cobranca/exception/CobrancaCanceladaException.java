package com.gestorpro.financeiro_service.cobranca.exception;

public class CobrancaCanceladaException extends RuntimeException {
    public CobrancaCanceladaException(int id) {
        super("Cobrança com id " + id + " está cancelada e não pode ser paga.");
    }
}
