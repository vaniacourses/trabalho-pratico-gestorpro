package com.gestorpro.auth_service.dto;

import com.gestorpro.auth_service.model.RoleName;

public record CreateUserDto(

        String email,
        String password,
        RoleName role

) {
}
