package com.gestorpro.gestao_projetos_service.controller;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestorpro.gestao_projetos_service.model.Projeto;
import com.gestorpro.gestao_projetos_service.service.FuncionarioService;

@RestController
@RequestMapping("/gproj/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping("/{idFuncionario}/projetos/{idProjeto}")
    public ResponseEntity<Void> associarProjeto(
            @PathVariable Long idFuncionario,
            @PathVariable Long idProjeto
    ) {
        funcionarioService.adicionarProjetoAoFuncionario(idFuncionario, idProjeto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idFuncionario}/projetos")
    public ResponseEntity<Set<Projeto>> listarProjetos(
            @PathVariable Long idFuncionario
    ) {
        Set<Projeto> projetos = funcionarioService.listarProjetosDoFuncionario(idFuncionario);
        return ResponseEntity.ok(projetos);
    }
}