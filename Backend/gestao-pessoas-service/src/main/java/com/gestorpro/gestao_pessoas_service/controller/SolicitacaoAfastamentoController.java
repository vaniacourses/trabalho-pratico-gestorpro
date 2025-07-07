package com.gestorpro.gestao_pessoas_service.controller;

import com.gestorpro.gestao_pessoas_service.model.SolicitacaoAfastamento;
import com.gestorpro.gestao_pessoas_service.service.ServicoDeAfastamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rh/solicitacoes-afastamento")
public class SolicitacaoAfastamentoController {

    @Autowired
    private ServicoDeAfastamento servicoDeAfastamento;

    @PostMapping
    public ResponseEntity<SolicitacaoAfastamento> criarSolicitacao(@RequestBody SolicitacaoAfastamento solicitacao) {
        SolicitacaoAfastamento novaSolicitacao = servicoDeAfastamento.solicitar(solicitacao);
        return new ResponseEntity<>(novaSolicitacao, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<SolicitacaoAfastamento> aprovarSolicitacao(@PathVariable Integer id) {
        SolicitacaoAfastamento solicitacaoAprovada = servicoDeAfastamento.aprovar(id);
        return ResponseEntity.ok(solicitacaoAprovada);
    }

    @PutMapping("/{id}/rejeitar")
    public ResponseEntity<SolicitacaoAfastamento> rejeitarSolicitacao(@PathVariable Integer id) {
        SolicitacaoAfastamento solicitacaoRejeitada = servicoDeAfastamento.rejeitar(id);
        return ResponseEntity.ok(solicitacaoRejeitada);
    }

    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<SolicitacaoAfastamento>> listarPorFuncionario(@PathVariable Integer idFuncionario) {
        List<SolicitacaoAfastamento> solicitacoes = servicoDeAfastamento.listarPorFuncionario(idFuncionario);
        return ResponseEntity.ok(solicitacoes);
    }
}