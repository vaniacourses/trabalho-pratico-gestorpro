package com.gestorpro.gestao_pessoas_service.controller;

import com.gestorpro.gestao_pessoas_service.model.Contrato;
import com.gestorpro.gestao_pessoas_service.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contratos") // Endpoints para contratos
public class ContratoController {

    @Autowired
    private ContratoRepository contratoRepository;

    // Endpoint para criar um novo contrato
    @PostMapping
    public ResponseEntity<Contrato> criarContrato(@RequestBody Contrato novoContrato) {
        // Para criar um contrato, o JSON enviado precisa ter um objeto "funcionario"
        // contendo apenas o "idFuncionario" de um funcionário que já existe.
        // Ex: { "tipo": "CLT", "jornada": 40, "funcionario": { "idFuncionario": 1 } }
        Contrato contratoSalvo = contratoRepository.save(novoContrato);
        return new ResponseEntity<>(contratoSalvo, HttpStatus.CREATED);
    }

    // Endpoint para listar todos os contratos
    @GetMapping
    public ResponseEntity<List<Contrato>> listarTodosContratos() {
        List<Contrato> todosOsContratos = contratoRepository.findAll();
        return new ResponseEntity<>(todosOsContratos, HttpStatus.OK);
    }

    // Endpoint BÔNUS: Listar todos os contratos de um funcionário específico
    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<Contrato>> listarContratosPorFuncionario(@PathVariable Integer idFuncionario) {
        List<Contrato> contratosDoFuncionario = contratoRepository.findByFuncionario_IdFuncionario(idFuncionario);
        return new ResponseEntity<>(contratosDoFuncionario, HttpStatus.OK);
    }
}
