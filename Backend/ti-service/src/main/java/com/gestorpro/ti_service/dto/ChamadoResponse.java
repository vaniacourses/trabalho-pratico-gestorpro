package com.gestorpro.ti_service.dto;

import com.gestorpro.ti_service.model.Status;
import java.time.LocalDateTime;

/**
 * DTO que representa a visão pública de um chamado.
 */
public record ChamadoResponse(
        Long id,
        String tipoProblema,
        String urgencia,
        String solicitanteEmail,
        Status status,
        LocalDateTime dataHoraCriacao
) {

}