package com.gestorpro.gestao_projetos_service.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestorpro.gestao_projetos_service.dto.ProjetoCriadoDto;
import com.gestorpro.gestao_projetos_service.dto.ProjetoDto;
import com.gestorpro.gestao_projetos_service.model.Projeto;
import com.gestorpro.gestao_projetos_service.service.ProjetoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/gproj/projetos")
public class ProjetoController {
    @Autowired
    private ProjetoService projetoService;

    @PostMapping("/create")
    public ResponseEntity<Projeto> postMethodName(@RequestBody ProjetoCriadoDto projeto) {
        Projeto proj = projetoService.createProjeto(projeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(proj);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoDto> recuperarProjetoPorId(@PathVariable Long id) {
        Optional<ProjetoDto> p = projetoService.buscarPorId(id);
        if(!p.isPresent()){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok().body(p.get());
        }
    }
    
    
}
