/*package com.gestorpro.gestao_pessoas_service.controller;

import com.gestorpro.gestao_pessoas_service.dto.UsuarioSenhaDto;
import com.gestorpro.gestao_pessoas_service.model.User;
import com.gestorpro.gestao_pessoas_service.model.Usuario;
import com.gestorpro.gestao_pessoas_service.service.ServicoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rh/usuarios")
public class UsuarioController {

    @Autowired
    private ServicoUsuario servicoUsuario;

    @GetMapping("/{id}")
    public ResponseEntity<User> buscarUsuarioPorId(@PathVariable Integer id) {
        // ATENÇÃO: Em um sistema real, este endpoint deveria retornar um DTO para não expor a senha!
        // Por simplicidade, estamos retornando a entidade.
        User usuario = servicoUsuario.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}/trocar-senha")
    public ResponseEntity<Void> trocarSenha(@PathVariable Integer id, @RequestBody UsuarioSenhaDto dto) {
        servicoUsuario.trocarSenha(id, dto);
        return ResponseEntity.noContent().build();
    }
}*/