package com.gestorpro.ti_service.controller;

import com.gestorpro.ti_service.dto.*;
import com.gestorpro.ti_service.model.Status;
import com.gestorpro.ti_service.service.ChamadoSuporteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// O recurso base para este controller continua sendo "/ti".
@RestController
@RequestMapping("/ti")
public class ControladorChamado {

    @Autowired
    private ChamadoSuporteService chamadoService;

    // Endpoints para Qualquer Usuário Autenticado

    // A URL para criar um chamado agora é "/ti/abrir-chamado".
    @PostMapping("/abrir-chamado")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ChamadoResponse> abrirChamado(@Valid @RequestBody AbrirChamadoRequest request,
                                                        @RequestHeader("X-Email") String emailSolicitante) {
        ChamadoResponse chamadoAberto = chamadoService.abrirChamado(request, emailSolicitante);
        return ResponseEntity.status(HttpStatus.CREATED).body(chamadoAberto);
    }

    @GetMapping("/meus-chamados")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ChamadoResponse>> listarMeusChamados(@RequestHeader("X-Email") String emailSolicitante) {
        List<ChamadoResponse> chamados = chamadoService.listarChamadosPorSolicitante(emailSolicitante);
        return ResponseEntity.ok(chamados);
    }

    @PutMapping("/{id}/cancelar")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> cancelarChamado(@PathVariable Long id,
                                                @Valid @RequestBody CancelarRequestDTO dto, // <-- AQUI ESTÁ A MUDANÇA
                                                @RequestHeader("X-Email") String emailAtor,
                                                Authentication authentication) {
        // Agora passamos o motivo do DTO para o serviço
        chamadoService.cancelarChamado(id, dto.motivo(), emailAtor, authentication.getAuthorities());
        return ResponseEntity.noContent().build();
    }


    // Endpoints Exclusivos para a Equipe

    //GET /ti ainda lista todos os chamados para a equipe de TI.
    @GetMapping
    @PreAuthorize("hasRole('TI')")
    public ResponseEntity<List<ChamadoResponse>> listarTodosOsChamados(
            @RequestParam(required = false) Status status) {
        List<ChamadoResponse> chamados = (status != null)
                ? chamadoService.listarChamadosPorStatus(status)
                : chamadoService.listarTodosChamados();
        return ResponseEntity.ok(chamados);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('TI')")
    public ResponseEntity<ChamadoResponse> buscarChamadoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(chamadoService.buscarChamadoPorId(id));
    }

    @PutMapping("/{id}/iniciar-atendimento")
    @PreAuthorize("hasRole('TI')")
    public ResponseEntity<Void> iniciarChamado(@PathVariable Long id) {
        chamadoService.iniciarAtendimento(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/resolver")
    @PreAuthorize("hasRole('TI')")
    public ResponseEntity<Void> resolverChamado(@PathVariable Long id, @Valid @RequestBody SolucaoRequestDTO dto) {
        chamadoService.resolverChamado(id, dto.solucao());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reabrir")
    @PreAuthorize("hasRole('TI')")
    public ResponseEntity<Void> reabrirChamado(@PathVariable Long id, @Valid @RequestBody ReabrirRequestDTO dto) {
        chamadoService.reabrirChamado(id, dto.motivo());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/fechar")
    @PreAuthorize("hasRole('TI')")
    public ResponseEntity<Void> fecharChamado(@PathVariable Long id, @RequestHeader("X-Email") String emailAtor) {
        chamadoService.fecharChamado(id, emailAtor);
        return ResponseEntity.noContent().build();
    }
}