package com.gestorpro.financeiro_service.cobranca.controller;

import com.gestorpro.financeiro_service.cobranca.model.dto.CobrancaRequestDTO;
import com.gestorpro.financeiro_service.cobranca.model.dto.CobrancaResponseDTO;
import com.gestorpro.financeiro_service.cobranca.service.CobrancaService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("financeiro/cobrancas")
@PreAuthorize("hasRole('ROLE_FIN')")
public class CobrancaController {
    private final CobrancaService service;

    public CobrancaController(CobrancaService service) {
        this.service = service;
    }

    @PostMapping("/agendar")
    public ResponseEntity<CobrancaResponseDTO> agendar(@RequestBody CobrancaRequestDTO dto) {
        return ResponseEntity.ok(service.agendarCobranca(dto));
    }

    @PutMapping("/{id}/receber")
    public ResponseEntity<CobrancaResponseDTO> receber(@PathVariable int id) {
        return ResponseEntity.ok(service.receberCobranca(id));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<CobrancaResponseDTO> cancelar(@PathVariable int id) {
        return ResponseEntity.ok(service.cancelarCobranca(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CobrancaResponseDTO> buscarCobranca(@PathVariable int id) {
        return ResponseEntity.ok(service.getCobranca(id));
    }

    @GetMapping
    public ResponseEntity<List<CobrancaResponseDTO>> listarTodasCobrancas() {
        return ResponseEntity.ok(service.listarCobrancas());
    }
}
