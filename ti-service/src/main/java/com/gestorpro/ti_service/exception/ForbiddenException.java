package com.gestorpro.ti_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção para tentativas de acesso não autorizado a recursos ou ações.
 * O usuário está autenticado, mas não tem a permissão necessária.
 * Mapeia para o status HTTP 403 Forbidden.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }
}