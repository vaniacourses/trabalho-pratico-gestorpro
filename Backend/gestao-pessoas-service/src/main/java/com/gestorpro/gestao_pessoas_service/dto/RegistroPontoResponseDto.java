package com.gestorpro.gestao_pessoas_service.dto;

import com.gestorpro.gestao_pessoas_service.model.StatusPonto;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class RegistroPontoResponseDto {
    private Integer idRegistro;
    private LocalDate data;
    private LocalTime horaEntrada;
    private LocalTime horaSaida;
    private StatusPonto status;
    private String justificativa;
    private Integer idFuncionario;
}