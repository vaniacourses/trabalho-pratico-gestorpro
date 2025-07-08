package com.gestorpro.gestao_pessoas_service.service;

import com.gestorpro.gestao_pessoas_service.dto.FuncionarioCreateDto;
import com.gestorpro.gestao_pessoas_service.dto.FuncionarioDto;
import com.gestorpro.gestao_pessoas_service.dto.FuncionarioUpdateDto;
import com.gestorpro.gestao_pessoas_service.model.Funcionario;
import com.gestorpro.gestao_pessoas_service.repository.*;
import com.gestorpro.gestao_pessoas_service.service.builder.FuncionarioBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicoFuncionario {

    // Injeção de todos os repositórios necessários para o Builder
    @Autowired private FuncionarioRepository funcionarioRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ContratoRepository contratoRepository;
    @Autowired private SalarioRepository salarioRepository;

    @Transactional
    public FuncionarioDto atualizarDados(Integer idFuncionario, FuncionarioUpdateDto dto) {
        // 1. Busca o funcionário existente no banco
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new RuntimeException("Funcionário com ID " + idFuncionario + " não encontrado."));

        // 2. Atualiza os campos com os novos valores do DTO
        funcionario.setNome(dto.getNome());
        funcionario.setCargo(dto.getCargo());
        funcionario.setDepartamento(dto.getDepartamento());
        funcionario.setTelefone(dto.getTelefone());
        funcionario.setNivel(dto.getNivel());

        // 3. Salva o funcionário atualizado
        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);

        // 4. Converte para DTO para a resposta
        return convertToDto(funcionarioSalvo);
    }

    @Transactional
    public Funcionario contratar(FuncionarioCreateDto dto) {
        // Instancia e usa o Builder para encapsular a lógica de criação
        FuncionarioBuilder builder = new FuncionarioBuilder(
            funcionarioRepository,
            usuarioRepository,
            contratoRepository,
            salarioRepository
        );

        return builder
                .comDadosPessoais(dto.getNome(), dto.getCargo(), dto.getDepartamento(), dto.getTelefone())
                .comCredenciais(dto.getEmail(), dto.getSenha())
                .comContratoInicial(dto.getTipoContrato(), dto.getJornada())
                .comSalarioInicial(dto.getSalarioInicial())
                .build();
    }

    public List<FuncionarioDto> listarTodos() {
        return funcionarioRepository.findAll()
                .stream()
                .map(this::convertToDto) // Converte cada Funcionario para FuncionarioDto
                .collect(Collectors.toList());
    }

    public FuncionarioDto buscarPorId(Integer idFuncionario) {
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado."));
        return convertToDto(funcionario);
    }
    
    @Transactional
    public void demitir(Integer idFuncionario) {
        if (!funcionarioRepository.existsById(idFuncionario)) {
            throw new RuntimeException("Funcionário não encontrado.");
        }
        funcionarioRepository.deleteById(idFuncionario);
    }
    
    // Método auxiliar para converter a entidade em DTO
    private FuncionarioDto convertToDto(Funcionario funcionario) {
        FuncionarioDto dto = new FuncionarioDto();
        dto.setIdFuncionario(funcionario.getIdFuncionario());
        dto.setNome(funcionario.getNome());
        dto.setCargo(funcionario.getCargo());
        dto.setDepartamento(funcionario.getDepartamento());
        dto.setDataAdmissao(funcionario.getData_admissao());
        if (funcionario.getUsuario() != null) {
            dto.setEmail(funcionario.getUsuario().getEmail());
        }
        return dto;
    }
}