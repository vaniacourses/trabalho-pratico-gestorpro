package com.gestorpro.gestao_pessoas_service.service;

import com.gestorpro.gestao_pessoas_service.dto.CreateUserDto;
import com.gestorpro.gestao_pessoas_service.dto.FuncionarioCreateDto;
import com.gestorpro.gestao_pessoas_service.dto.FuncionarioDto;
import com.gestorpro.gestao_pessoas_service.dto.FuncionarioUpdateDto;
import com.gestorpro.gestao_pessoas_service.dto.UsuarioCreateDto;
import com.gestorpro.gestao_pessoas_service.dto.UsuarioDto;
import com.gestorpro.gestao_pessoas_service.model.Funcionario;
import com.gestorpro.gestao_pessoas_service.model.User;
import com.gestorpro.gestao_pessoas_service.repository.*;
import com.gestorpro.gestao_pessoas_service.service.builder.FuncionarioBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicoFuncionario {

    private final RestTemplate restTemplate;

    @Autowired private FuncionarioRepository funcionarioRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ContratoRepository contratoRepository;
    @Autowired private SalarioRepository salarioRepository;
    
    public ServicoFuncionario(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Transactional
    public FuncionarioDto atualizarDados(Integer idFuncionario, FuncionarioUpdateDto dto) {
        // Busca o funcionário existente no banco
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new RuntimeException("Funcionário com ID " + idFuncionario + " não encontrado."));

        // Atualiza os campos com os novos valores do DTO
        funcionario.setNome(dto.getNome());
        funcionario.setCargo(dto.getCargo());
        funcionario.setDepartamento(dto.getDepartamento());
        funcionario.setTelefone(dto.getTelefone());
        funcionario.setNivel(dto.getNivel());

        // Salva o funcionário atualizado
        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);

        // Converte para DTO para a resposta
        return convertToDto(funcionarioSalvo);
    }

    @Transactional
    public Funcionario contratar(FuncionarioCreateDto dto, CreateUserDto user) {
        String url = "http://localhost:8081/auth/create"; // URL do outro microsserviço

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateUserDto> request = new HttpEntity<>(user, headers);

        User usuarioDto = restTemplate.postForObject(url, request, User.class);
        
        User userBanco = usuarioRepository.findById(usuarioDto.getId())
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


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
                .setUsuario(userBanco)
                .build();
    }

    public List<FuncionarioDto> listarTodos() {
        return funcionarioRepository.findAll()
                .stream()
                .map(this::convertToDto)
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