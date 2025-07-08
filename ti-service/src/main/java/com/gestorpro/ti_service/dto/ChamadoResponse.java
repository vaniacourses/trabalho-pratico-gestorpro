package com.gestorpro.ti_service.dto;

import com.gestorpro.ti_service.model.Status;

import java.time.LocalDateTime;

public record ChamadoResponse(
        Long id,
        String tipoProblema,
        String urgencia,
        String solicitanteEmail,
        Status status,
        LocalDateTime dataHoraCriacao
) {

}