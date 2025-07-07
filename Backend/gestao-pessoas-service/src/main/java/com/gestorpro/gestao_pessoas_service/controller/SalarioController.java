package com.gestorpro.gestao_pessoas_service.controller;

import com.gestorpro.gestao_pessoas_service.model.Salario;
import com.gestorpro.gestao_pessoas_service.repository.SalarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salarios") // Endpoints para salários
public class SalarioController {

    @Autowired
    private SalarioRepository salarioRepository;

    // Endpoint para criar um novo registo de salário
    @PostMapping
    public ResponseEntity<Salario> criarSalario(@RequestBody Salario novoSalario) {
        // Exemplo de JSON: { "valor": 5500.75, "dataPagamento": "2025-07-05", "funcionario": { "idFuncionario": 1 } }
        Salario salarioSalvo = salarioRepository.save(novoSalario);
        return new ResponseEntity<>(salarioSalvo, HttpStatus.CREATED);
    }

    // Endpoint para listar todos os registos de salário
    @GetMapping
    public ResponseEntity<List<Salario>> listarTodosSalarios() {
        List<Salario> todosOsSalarios = salarioRepository.findAll();
        return new ResponseEntity<>(todosOsSalarios, HttpStatus.OK);
    }

    // Endpoint para listar todos os salários de um funcionário específico
    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<Salario>> listarSalariosPorFuncionario(@PathVariable Integer idFuncionario) {
        List<Salario> salariosDoFuncionario = salarioRepository.findByFuncionario_IdFuncionario(idFuncionario);
        return new ResponseEntity<>(salariosDoFuncionario, HttpStatus.OK);
    }
}
