package com.gestorpro.gestao_pessoas_service.service;

import com.gestorpro.gestao_pessoas_service.dto.UsuarioSenhaDto;
import com.gestorpro.gestao_pessoas_service.model.User;
import com.gestorpro.gestao_pessoas_service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicoUsuario {

    /*@Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void trocarSenha(Integer idUsuario, UsuarioSenhaDto dto) {
        User usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário com ID " + idUsuario + " não encontrado."));

        // Criptografa a nova senha antes de salvar
        usuario.setSenha(dto.getNovaSenha());

        usuarioRepository.save(usuario);
    }

    public User buscarPorId(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }*/
}