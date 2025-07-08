package com.gestorpro.ti_service.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para receber os dados do formulário de solução de um chamado.
 * Usado no endpoint: PUT /chamados-suporte/{id}/resolver
 */
public record SolucaoRequestDTO(
        @NotBlank(message = "A descrição da solução não pode ser vazia.")
        String solucao
) {
}