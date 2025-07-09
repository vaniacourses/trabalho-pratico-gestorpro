package com.gestorpro.gestao_pessoas_service.dto;

import com.gestorpro.gestao_pessoas_service.model.RoleName;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateUserDto {

        String email;
        String password;
        String role;
}
