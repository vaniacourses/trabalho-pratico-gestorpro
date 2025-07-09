package com.gestorpro.gestao_projetos_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestorpro.gestao_projetos_service.dto.ProjetoDto;
import com.gestorpro.gestao_projetos_service.model.Projeto;
import com.gestorpro.gestao_projetos_service.service.ProjetoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/gproj/projetos")
public class ProjetoController {
    @Autowired
    private ProjetoService projetoService;

    @PostMapping("/create")
    public ResponseEntity<Projeto> postMethodName(@RequestBody ProjetoDto projeto) {
        Projeto proj = projetoService.createProjeto(projeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(proj);
    }
    
}
