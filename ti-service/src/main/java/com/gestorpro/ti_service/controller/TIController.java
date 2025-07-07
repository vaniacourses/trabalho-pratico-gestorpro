package com.gestorpro.ti_service.controller;

import com.gestorpro.ti_service.dto.ChamadoResponse;
import com.gestorpro.ti_service.dto.MensagemDTO;
import com.gestorpro.ti_service.service.ChamadoSuporteService; // Nome corrigido
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ti")
public class TIController {

    // Injetando o serviço correto
    @Autowired
    private ChamadoSuporteService chamadoSuporteService;

    @GetMapping("/publico")
    public ResponseEntity<MensagemDTO> acessoBasico(@RequestHeader("X-Email") String email,
                                                    @RequestHeader("X-Roles") String roles) {
        String mensagem = String.format("Olá, %s! Seu acesso é básico. Roles: %s", email, roles);
        return ResponseEntity.ok(new MensagemDTO(mensagem));
    }

    @GetMapping("/restrito")
    @PreAuthorize("hasRole('TI')")
    public ResponseEntity<MensagemDTO> acessoRestrito(@RequestHeader("X-Email") String email) {
        String mensagem = String.format("Acesso total concedido para funcionário da TI: %s", email);
        return ResponseEntity.ok(new MensagemDTO(mensagem));
    }

    // NOVO ENDPOINT: Listar todos os chamados, uma responsabilidade do pessoal de TI
    @GetMapping("/chamados")
    @PreAuthorize("hasRole('TI')")
    public ResponseEntity<List<ChamadoResponse>> listarTodosChamados() {
        List<ChamadoResponse> chamados = chamadoSuporteService.listarTodosChamados();
        return ResponseEntity.ok(chamados);
    }
}