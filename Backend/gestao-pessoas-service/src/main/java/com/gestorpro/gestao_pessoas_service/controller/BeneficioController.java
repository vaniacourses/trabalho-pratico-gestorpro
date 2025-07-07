package com.gestorpro.gestao_pessoas_service.controller;

import com.gestorpro.gestao_pessoas_service.dto.BeneficioDto;
import com.gestorpro.gestao_pessoas_service.model.Beneficio;
import com.gestorpro.gestao_pessoas_service.service.ServicoDeBeneficios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rh/beneficios")
public class BeneficioController {

    @Autowired
    private ServicoDeBeneficios servicoDeBeneficios;

    // Endpoint para conceder (criar) um novo benefício
    @PostMapping
    public ResponseEntity<Beneficio> concederBeneficio(@RequestBody BeneficioDto beneficioDto) {
        Beneficio novoBeneficio = servicoDeBeneficios.concederBeneficio(beneficioDto);
        return new ResponseEntity<>(novoBeneficio, HttpStatus.CREATED);
    }

    // Endpoint para listar todos os benefícios de um funcionário
    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<Beneficio>> listarPorFuncionario(@PathVariable Integer idFuncionario) {
        List<Beneficio> beneficios = servicoDeBeneficios.listarPorFuncionario(idFuncionario);
        return ResponseEntity.ok(beneficios);
    }

    // Endpoint para revogar (deletar) um benefício
    @DeleteMapping("/{idBeneficio}")
    public ResponseEntity<Void> revogarBeneficio(@PathVariable Integer idBeneficio) {
        servicoDeBeneficios.revogarBeneficio(idBeneficio);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content, indicando sucesso na remoção
    }
}