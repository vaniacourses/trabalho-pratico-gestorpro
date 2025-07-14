package com.gestorpro.gestao_projetos_service.dto;

import java.time.LocalDate;

import lombok.Data;

@Data 
public class ProjetoFuncionarioDto {
    private Long id;
    private String tipo;
    private String cliente;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private String status;
}