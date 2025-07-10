package com.gestorpro.ti_service.dto;

import jakarta.validation.constraints.NotBlank;

public record CancelarRequestDTO(
        @NotBlank(message = "O motivo do cancelamento não pode ser vazio.")
        String motivo
) {}