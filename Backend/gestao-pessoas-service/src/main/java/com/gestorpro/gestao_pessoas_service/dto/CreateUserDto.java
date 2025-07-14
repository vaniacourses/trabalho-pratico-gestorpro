package com.gestorpro.gestao_pessoas_service.dto;

import com.gestorpro.gestao_pessoas_service.model.RoleName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CreateUserDto {

        String email;
        String password;
        String role;
}
