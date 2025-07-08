package com.gestorpro.gestao_pessoas_service.dto;

import lombok.Data;

@Data
public class UsuarioSenhaDto {
    // Em um sistema real, você poderia incluir a senha antiga para verificação.
    // private String senhaAntiga;
    private String novaSenha;
}