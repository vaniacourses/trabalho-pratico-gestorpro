package com.gestorpro.gestao_projetos_service.dto;

import java.util.Set;

import lombok.Data;

@Data
public class FuncionarioProjetoDto {
    private Integer id;
    private String nome;
    private String email;

    private Set<ProjetoDto> projetos;
}
