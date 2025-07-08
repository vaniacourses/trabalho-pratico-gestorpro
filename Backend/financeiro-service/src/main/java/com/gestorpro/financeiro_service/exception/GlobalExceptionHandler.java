package com.gestorpro.financeiro_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gestorpro.financeiro_service.cobranca.exception.*;
import com.gestorpro.financeiro_service.despesa.exception.DespesaNaoEncontradaException;
import com.gestorpro.financeiro_service.despesa.exception.DespesaProcessadaException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CobrancaNaoEncontradaException.class)
    public ResponseEntity<String> handleCobrancaNaoEncontrada(CobrancaNaoEncontradaException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CobrancaCanceladaException.class)
    public ResponseEntity<String> handleCobrancaCancelada(CobrancaCanceladaException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(CobrancaPagaException.class)
    public ResponseEntity<String> handleCobrancaPaga(CobrancaPagaException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(ErroComunicacaoBancoException.class)
    public ResponseEntity<String> handleErroComunicacaoBanco(ErroComunicacaoBancoException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOther(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
    }

    @ExceptionHandler(DespesaNaoEncontradaException.class)
    public ResponseEntity<String> handleDespesaNaoEncontrada(DespesaNaoEncontradaException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(DespesaProcessadaException.class)
    public ResponseEntity<String> handleDespesaProcessada(DespesaProcessadaException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
