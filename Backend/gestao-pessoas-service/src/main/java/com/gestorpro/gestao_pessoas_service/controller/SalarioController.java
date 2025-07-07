package com.gestorpro.gestao_pessoas_service.controller;

import com.gestorpro.gestao_pessoas_service.dto.SalarioDto;
import com.gestorpro.gestao_pessoas_service.model.Salario;
import com.gestorpro.gestao_pessoas_service.service.ServicoDeSalario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rh/salarios")
public class SalarioController {

    @Autowired
    private ServicoDeSalario servicoDeSalario;

    @PostMapping
    public ResponseEntity<Salario> registrarPagamento(@RequestBody SalarioDto salarioDto) {
        Salario novoSalario = servicoDeSalario.registrarPagamento(salarioDto);
        return new ResponseEntity<>(novoSalario, HttpStatus.CREATED);
    }

    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<Salario>> listarHistoricoPorFuncionario(@PathVariable Integer idFuncionario) {
        List<Salario> historico = servicoDeSalario.listarHistoricoPorFuncionario(idFuncionario);
        return ResponseEntity.ok(historico);
    }
}