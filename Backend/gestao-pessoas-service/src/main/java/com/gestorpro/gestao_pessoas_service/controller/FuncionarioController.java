package com.gestorpro.gestao_pessoas_service.controller;

import com.gestorpro.gestao_pessoas_service.dto.CreateUserDto;
import com.gestorpro.gestao_pessoas_service.dto.FuncionarioCreateDto;
import com.gestorpro.gestao_pessoas_service.dto.FuncionarioDto;
import com.gestorpro.gestao_pessoas_service.dto.FuncionarioUpdateDto;
import com.gestorpro.gestao_pessoas_service.dto.UsuarioCreateDto;
import com.gestorpro.gestao_pessoas_service.dto.UsuarioDto;
import com.gestorpro.gestao_pessoas_service.model.Funcionario;
import com.gestorpro.gestao_pessoas_service.service.ServicoFuncionario;

import jakarta.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/rh/funcionarios")
public class FuncionarioController {

    @Autowired
    private ServicoFuncionario servicoFuncionario;

    @PostMapping("/create")
    public ResponseEntity<Funcionario> contratarFuncionario(@RequestBody FuncionarioCreateDto createDto) {
        CreateUserDto usuarioCreateDto = new CreateUserDto(createDto.getEmail(), createDto.getSenha(), createDto.getCargo());

        Funcionario novoFuncionario = servicoFuncionario.contratar(createDto, usuarioCreateDto);
        // Converte a entidade criada para o DTO de resposta
        // responseDto = servicoFuncionario.buscarPorId(novoFuncionario.getIdFuncionario());
        return new ResponseEntity<>(novoFuncionario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDto> atualizarFuncionario(@PathVariable Integer id, @RequestBody FuncionarioUpdateDto updateDto) {
        FuncionarioDto funcionarioAtualizado = servicoFuncionario.atualizarDados(id, updateDto);
        return ResponseEntity.ok(funcionarioAtualizado);
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioDto>> listarFuncionarios() {
        List<FuncionarioDto> funcionarios = servicoFuncionario.listarTodos();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDto> buscarFuncionarioPorId(@PathVariable Integer id) {
        FuncionarioDto funcionario = servicoFuncionario.buscarPorId(id);
        return ResponseEntity.ok(funcionario);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> buscarFuncionarioPorEmail(@PathVariable String email) {
        try{
            FuncionarioDto funcionatio = servicoFuncionario.buscarPorEmail(email);
            return ResponseEntity.ok().body(funcionatio);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> demitirFuncionario(@PathVariable Integer id) {
        servicoFuncionario.demitir(id);
        return ResponseEntity.noContent().build();
    }
}