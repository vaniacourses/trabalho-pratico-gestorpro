package com.gestorpro.gestao_pessoas_service.controller;

import com.gestorpro.gestao_pessoas_service.model.Funcionario;
import com.gestorpro.gestao_pessoas_service.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios") // Todos os endpoints nesta classe começarão com /api/funcionarios
public class FuncionarioController {

    @Autowired // Injeção de dependência: O Spring nos dará uma instância do repositório.
    private FuncionarioRepository funcionarioRepository;

    // Endpoint para criar um novo funcionário (HTTP POST) 
    @PostMapping
    public ResponseEntity<Funcionario> criarFuncionario(@RequestBody Funcionario novoFuncionario) {
        Funcionario funcionarioSalvo = funcionarioRepository.save(novoFuncionario);
        return new ResponseEntity<>(funcionarioSalvo, HttpStatus.CREATED);
    }

    // Endpoint para listar todos os funcionários (HTTP GET)
    @GetMapping
    public ResponseEntity<List<Funcionario>> listarTodosFuncionarios() {
        List<Funcionario> todosOsFuncionarios = funcionarioRepository.findAll();
        return new ResponseEntity<>(todosOsFuncionarios, HttpStatus.OK);
    }
}
