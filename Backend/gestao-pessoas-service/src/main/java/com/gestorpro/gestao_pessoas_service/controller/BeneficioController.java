package com.gestorpro.gestao_pessoas_service.controller;

import com.gestorpro.gestao_pessoas_service.model.Beneficio;
import com.gestorpro.gestao_pessoas_service.repository.BeneficioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beneficios") // Endpoints para benefícios
public class BeneficioController {

    @Autowired
    private BeneficioRepository beneficioRepository;

    // Endpoint para criar um novo benefício para um funcionário
    @PostMapping
    public ResponseEntity<Beneficio> criarBeneficio(@RequestBody Beneficio novoBeneficio) {
        // Exemplo de JSON: { "nome": "Vale Refeição", "valor": 650.00, "funcionario": { "idFuncionario": 1 } }
        Beneficio beneficioSalvo = beneficioRepository.save(novoBeneficio);
        return new ResponseEntity<>(beneficioSalvo, HttpStatus.CREATED);
    }

    // Endpoint para listar todos os benefícios registados
    @GetMapping
    public ResponseEntity<List<Beneficio>> listarTodosBeneficios() {
        List<Beneficio> todosOsBeneficios = beneficioRepository.findAll();
        return new ResponseEntity<>(todosOsBeneficios, HttpStatus.OK);
    }

    // Endpoint para listar todos os benefícios de um funcionário específico
    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<Beneficio>> listarBeneficiosPorFuncionario(@PathVariable Integer idFuncionario) {
        List<Beneficio> beneficiosDoFuncionario = beneficioRepository.findByFuncionario_IdFuncionario(idFuncionario);
        return new ResponseEntity<>(beneficiosDoFuncionario, HttpStatus.OK);
    }
}
