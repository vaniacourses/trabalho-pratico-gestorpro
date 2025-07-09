package com.gestorpro.gestao_pessoas_service.dto;

import lombok.Data;

@Data
public class UsuarioDto {
    private Long id;
    private String email;
    private String senha;
    private String role;
}