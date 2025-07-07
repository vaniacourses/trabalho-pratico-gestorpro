package com.gestorpro.ti_service.dto;

import com.gestorpro.ti_service.model.Status;

import java.time.LocalDateTime;

public record ChamadoResponse(
        Long id,
        String titulo,
        Status status,
        String solicitanteEmail
) {
    public ChamadoResponse(Long id, String tipoProblema, String urgencia, String solicitanteEmail, Status status, LocalDateTime dataHoraCriacao) {
    }
}