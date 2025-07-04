package com.gestorpro.teste_service.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String getHelloAdmin(@RequestHeader("X-Email") String email,
                                @RequestHeader("X-Roles") String roles) {
        return String.format("Olá, ADMIN %s que os papéis são: %s!", email, roles);
    }

    @PreAuthorize("hasRole('RH')")
    @GetMapping("/arquivos")
    public String getArquivos() {
        return "Retornando arquivos...";
    }
}
