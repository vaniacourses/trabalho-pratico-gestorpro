package com.gestorpro.gestao_pessoas_service.service;

import com.gestorpro.gestao_pessoas_service.dto.UsuarioSenhaDto;
import com.gestorpro.gestao_pessoas_service.model.Usuario;
import com.gestorpro.gestao_pessoas_service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicoUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Injetaremos o PasswordEncoder que será configurado no Spring Security
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * A criação de usuários é tratada pelo ServicoFuncionario através do FuncionarioBuilder,
     * garantindo que todo usuário esteja associado a um funcionário.
     */

    @Transactional
    public void trocarSenha(Integer idUsuario, UsuarioSenhaDto dto) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário com ID " + idUsuario + " não encontrado."));

        // Criptografa a nova senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(dto.getNovaSenha()));

        usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }
}