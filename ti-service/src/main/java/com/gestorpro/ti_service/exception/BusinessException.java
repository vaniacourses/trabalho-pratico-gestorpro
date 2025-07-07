package com.gestorpro.ti_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção para violações de regras de negócio.
 * Ex: Tentar executar uma ação em um estado inválido de um objeto.
 * Mapeia para o status HTTP 409 Conflict.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}