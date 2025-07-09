package com.gestorpro.gestao_pessoas_service.controller;

import com.gestorpro.gestao_pessoas_service.dto.BeneficioDto;
import com.gestorpro.gestao_pessoas_service.dto.BeneficioResponseDto;
import com.gestorpro.gestao_pessoas_service.service.ServicoDeBeneficios;
import jakarta.validation.Valid;
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

    @PostMapping
    public ResponseEntity<BeneficioResponseDto> concederBeneficio(@Valid @RequestBody BeneficioDto beneficioDto) {
        BeneficioResponseDto responseDto = servicoDeBeneficios.concederBeneficio(beneficioDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<BeneficioResponseDto>> listarPorFuncionario(@PathVariable Integer idFuncionario) {
        List<BeneficioResponseDto> beneficiosDto = servicoDeBeneficios.listarPorFuncionario(idFuncionario);
        return ResponseEntity.ok(beneficiosDto);
    }

    @DeleteMapping("/{idBeneficio}")
    public ResponseEntity<Void> revogarBeneficio(@PathVariable Integer idBeneficio) {
        servicoDeBeneficios.revogarBeneficio(idBeneficio);
        return ResponseEntity.noContent().build();
    }
}