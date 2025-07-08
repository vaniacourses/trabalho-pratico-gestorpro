package com.gestorpro.ti_service.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para receber os dados do formulário de criação de um novo chamado.
 * Usado em: POST /chamados-suporte
 */
public record AbrirChamadoRequest(
        @NotBlank(message = "O tipo do problema não pode ser vazio.")
        String tipoProblema,
        @NotBlank(message = "A urgência não pode ser vazia.")
        String urgencia
) {
        // O e-mail do solicitante é pego do header da requisição, por isso não está aqui.
        // O status e a data de criação são definidos pelo sistema, por isso também não estão aqui.
}