package com.gestorpro.auth_service.dto;

import java.util.List;

import com.gestorpro.auth_service.model.Role;

public record RecoveryUserDto(

        Long id,
        String email,
        List<Role> roles

) {
}