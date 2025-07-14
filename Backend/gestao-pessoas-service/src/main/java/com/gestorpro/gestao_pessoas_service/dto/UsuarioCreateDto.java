package com.gestorpro.gestao_pessoas_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UsuarioCreateDto {
    private String email;
    private String senha;
    private String role;
}