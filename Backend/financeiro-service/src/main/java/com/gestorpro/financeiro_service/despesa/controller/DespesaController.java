package com.gestorpro.financeiro_service.despesa.controller;

import com.gestorpro.financeiro_service.despesa.model.dto.DespesaRejectedDTO;
import com.gestorpro.financeiro_service.despesa.model.dto.DespesaRequestDTO;
import com.gestorpro.financeiro_service.despesa.model.dto.DespesaResponseDTO;
import com.gestorpro.financeiro_service.despesa.service.DespesaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/financeiro/despesas")
public class DespesaController {

    private final DespesaService service;

    public DespesaController(DespesaService service) {
        this.service = service;
    }

    @PostMapping("/criar")
    public ResponseEntity<DespesaResponseDTO> criarSolicitacao(@RequestBody DespesaRequestDTO dto) {
        return ResponseEntity.ok(service.criarSolicitacao(dto));
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<DespesaResponseDTO> aprovarSolicitacao(@PathVariable int id) {
        return ResponseEntity.ok(service.aprovarSolicitacao(id));
    }

    @PutMapping("/{id}/reprovar")
    public ResponseEntity<DespesaResponseDTO> reprovarSolicitacao(@PathVariable int id, @RequestBody DespesaRejectedDTO dto) {
        return ResponseEntity.ok(service.reprovarSolicitacao(id, dto));
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<DespesaResponseDTO>> listarPendentes() {
        return ResponseEntity.ok(service.listarSolicitacoesPendentes());
    }

    @GetMapping
    public ResponseEntity<List<DespesaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarSolicitacoes());
    }
}
