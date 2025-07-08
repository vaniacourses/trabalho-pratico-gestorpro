package com.gestorpro.financeiro_service.cobranca.exception;

public class ErroComunicacaoBancoException extends RuntimeException {
    public ErroComunicacaoBancoException(String message) {
        super("Não foi possível se comunicar com o banco: " + message);
    }
}
