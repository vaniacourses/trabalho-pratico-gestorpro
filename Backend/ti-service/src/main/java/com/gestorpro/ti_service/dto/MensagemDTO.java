package com.gestorpro.ti_service.dto;

/**
 * DTO genérico para encapsular mensagens de texto simples em uma resposta JSON.
 * Útil para retornos de status ou erros simples. Ex: { "mensagem": "Operação realizada com sucesso." }
 */
public record MensagemDTO(String mensagem) {
}