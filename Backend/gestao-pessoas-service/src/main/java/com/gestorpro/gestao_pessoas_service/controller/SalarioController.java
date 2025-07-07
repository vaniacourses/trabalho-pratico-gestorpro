package com.gestorpro.gestao_pessoas_service.controller;

import com.gestorpro.gestao_pessoas_service.dto.SalarioDto;
import com.gestorpro.gestao_pessoas_service.dto.SalarioResponseDto; // Importe o DTO de resposta
import com.gestorpro.gestao_pessoas_service.service.ServicoDeSalario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rh/salarios") // Usando /rh/ como base
public class SalarioController {

    @Autowired
    private ServicoDeSalario servicoDeSalario;

    @PostMapping
    public ResponseEntity<SalarioResponseDto> registrarPagamento(@Valid @RequestBody SalarioDto salarioDto) {
        SalarioResponseDto responseDto = servicoDeSalario.registrarPagamento(salarioDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<SalarioResponseDto>> listarHistoricoPorFuncionario(@PathVariable Integer idFuncionario) {
        List<SalarioResponseDto> historicoDto = servicoDeSalario.listarHistoricoPorFuncionario(idFuncionario);
        return ResponseEntity.ok(historicoDto);
    }
}