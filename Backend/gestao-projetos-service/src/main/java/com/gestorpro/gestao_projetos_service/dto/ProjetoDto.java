package com.gestorpro.gestao_projetos_service.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.Data;

@Data 
public class ProjetoDto {
    private Long id;
    private String tipo;
    private String cliente;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private String status;
    private Set<FuncionarioDto> funcionarios;
}