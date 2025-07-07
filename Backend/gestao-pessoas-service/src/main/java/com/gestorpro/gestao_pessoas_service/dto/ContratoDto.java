package com.gestorpro.gestao_pessoas_service.dto;

import lombok.Data;

@Data
public class ContratoDto {
    private String tipo;
    private Integer jornada;
    private Integer idFuncionario;
}