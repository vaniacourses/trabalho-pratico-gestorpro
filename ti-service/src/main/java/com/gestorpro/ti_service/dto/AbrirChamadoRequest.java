package com.gestorpro.ti_service.dto;

import jakarta.validation.constraints.NotBlank;

public record AbrirChamadoRequest(
        @NotBlank(message = "O tipo do problema não pode ser vazio.")
        String tipoProblema,

        @NotBlank(message = "A urgência não pode ser vazia.")
        String urgencia
) {
}