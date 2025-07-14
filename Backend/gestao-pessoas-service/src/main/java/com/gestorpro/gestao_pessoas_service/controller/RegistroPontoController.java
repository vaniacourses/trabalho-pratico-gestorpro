package com.gestorpro.gestao_pessoas_service.controller;

import com.gestorpro.gestao_pessoas_service.dto.RegistroPontoDto;
import com.gestorpro.gestao_pessoas_service.dto.RegistroPontoResponseDto;
import com.gestorpro.gestao_pessoas_service.service.ServicoDePonto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rh/ponto") // Usando /rh/ como base
public class RegistroPontoController {

    @Autowired
    private ServicoDePonto servicoDePonto;

    @PostMapping("/entrada")
    public ResponseEntity<RegistroPontoResponseDto> registrarEntrada(@Valid @RequestBody RegistroPontoDto dto) {
        RegistroPontoResponseDto responseDto = servicoDePonto.registrarEntrada(dto.getIdFuncionario());
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/saida")
    public ResponseEntity<RegistroPontoResponseDto> registrarSaida(@Valid @RequestBody RegistroPontoDto dto) {
        RegistroPontoResponseDto responseDto = servicoDePonto.registrarSaida(dto.getIdFuncionario());
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}/validar")
    public ResponseEntity<RegistroPontoResponseDto> validarPonto(@PathVariable("id") Integer idRegistro) {
        RegistroPontoResponseDto responseDto = servicoDePonto.validar(idRegistro);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<RegistroPontoResponseDto>> listarPorFuncionario(@PathVariable Integer idFuncionario) {
        List<RegistroPontoResponseDto> responseDtos = servicoDePonto.listarPorFuncionario(idFuncionario);
        return ResponseEntity.ok(responseDtos);
    }
}