package com.gestorpro.gestao_pessoas_service.controller;

import com.gestorpro.gestao_pessoas_service.dto.SolicitacaoAfastamentoResponseDto;
import com.gestorpro.gestao_pessoas_service.model.SolicitacaoAfastamento;
import com.gestorpro.gestao_pessoas_service.service.ServicoDeAfastamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rh/solicitacoes-afastamento") // Usando /rh/ como base
public class SolicitacaoAfastamentoController {

    @Autowired
    private ServicoDeAfastamento servicoDeAfastamento;

    @PostMapping
    public ResponseEntity<SolicitacaoAfastamentoResponseDto> criarSolicitacao(@RequestBody SolicitacaoAfastamento solicitacao) {
        SolicitacaoAfastamentoResponseDto novoDto = servicoDeAfastamento.solicitar(solicitacao);
        return new ResponseEntity<>(novoDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<SolicitacaoAfastamentoResponseDto> aprovarSolicitacao(@PathVariable Integer id) {
        SolicitacaoAfastamentoResponseDto dtoAprovado = servicoDeAfastamento.aprovar(id);
        return ResponseEntity.ok(dtoAprovado);
    }

    @PutMapping("/{id}/rejeitar")
    public ResponseEntity<SolicitacaoAfastamentoResponseDto> rejeitarSolicitacao(@PathVariable Integer id) {
        SolicitacaoAfastamentoResponseDto dtoRejeitado = servicoDeAfastamento.rejeitar(id);
        return ResponseEntity.ok(dtoRejeitado);
    }

    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<SolicitacaoAfastamentoResponseDto>> listarPorFuncionario(@PathVariable Integer idFuncionario) {
        List<SolicitacaoAfastamentoResponseDto> dtos = servicoDeAfastamento.listarPorFuncionario(idFuncionario);
        return ResponseEntity.ok(dtos);
    }
}