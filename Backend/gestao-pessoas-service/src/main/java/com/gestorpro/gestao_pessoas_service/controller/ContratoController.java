package com.gestorpro.gestao_pessoas_service.controller;

import com.gestorpro.gestao_pessoas_service.dto.ContratoDto;
import com.gestorpro.gestao_pessoas_service.model.Contrato;
import com.gestorpro.gestao_pessoas_service.service.ServicoDeContrato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rh/contratos")
public class ContratoController {

    @Autowired
    private ServicoDeContrato servicoDeContrato;

    @PostMapping
    public ResponseEntity<Contrato> criarContrato(@RequestBody ContratoDto contratoDto) {
        Contrato novoContrato = servicoDeContrato.criarContrato(contratoDto);
        return new ResponseEntity<>(novoContrato, HttpStatus.CREATED);
    }

    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<Contrato>> listarPorFuncionario(@PathVariable Integer idFuncionario) {
        List<Contrato> contratos = servicoDeContrato.listarPorFuncionario(idFuncionario);
        return ResponseEntity.ok(contratos);
    }
    
    @PutMapping("/{idContrato}")
    public ResponseEntity<Contrato> atualizarContrato(@PathVariable Integer idContrato, @RequestBody ContratoDto contratoDto) {
        Contrato contratoAtualizado = servicoDeContrato.atualizarContrato(idContrato, contratoDto);
        return ResponseEntity.ok(contratoAtualizado);
    }

    @DeleteMapping("/{idContrato}")
    public ResponseEntity<Void> rescindirContrato(@PathVariable Integer idContrato) {
        servicoDeContrato.rescindirContrato(idContrato);
        return ResponseEntity.noContent().build();
    }
}