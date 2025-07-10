package com.gestorpro.ti_service.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para receber os dados do formulário de reabertura de um chamado.
 * Usado no endpoint: PUT /chamados-suporte/{id}/reabrir
 */
public record ReabrirRequestDTO(
        @NotBlank(message = "O motivo da reabertura não pode ser vazio.")
        String motivo
) {
}