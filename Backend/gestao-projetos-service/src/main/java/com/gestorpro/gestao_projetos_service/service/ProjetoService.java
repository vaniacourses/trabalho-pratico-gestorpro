package com.gestorpro.gestao_projetos_service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestorpro.gestao_projetos_service.dto.FuncionarioDto;
import com.gestorpro.gestao_projetos_service.dto.ProjetoCriadoDto;
import com.gestorpro.gestao_projetos_service.dto.ProjetoDto;
import com.gestorpro.gestao_projetos_service.model.Funcionario;
import com.gestorpro.gestao_projetos_service.model.Projeto;
import com.gestorpro.gestao_projetos_service.repository.FuncionarioRepository;
import com.gestorpro.gestao_projetos_service.repository.ProjetoRepository;

@Service
public class ProjetoService {
    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired 
    private FuncionarioRepository funcionarioRepository;

    public Projeto createProjeto(ProjetoCriadoDto proj){
        Projeto projeto = Projeto.builder()
                            .cliente(proj.getCliente())
                            .dataInicio(proj.getDataInicio())
                            .dataTermino(proj.getDataTermino())
                            .tipo(proj.getTipo())
                            .status(proj.getStatus())
                            .build();
        return projetoRepository.save(projeto);
    }

     public Optional<ProjetoDto> buscarPorId(Long id) {
        return projetoRepository.findById(id).map(this::convertToDto);
    }

    private ProjetoDto convertToDto(Projeto projeto) {
        ProjetoDto dto = new ProjetoDto();
        dto.setId(projeto.getId());
        dto.setTipo(projeto.getTipo());
        dto.setCliente(projeto.getCliente());
        dto.setDataInicio(projeto.getDataInicio());
        dto.setDataTermino(projeto.getDataTermino());
        dto.setStatus(projeto.getStatus());
        
        if (projeto.getFuncionarios() != null) {
            dto.setFuncionarios(
                projeto.getFuncionarios().stream()
                       .map(this::convertFuncionarioToDto)
                       .collect(Collectors.toSet())
            );
        }
        return dto;
    }

    private FuncionarioDto convertFuncionarioToDto(Funcionario funcionario) {
        FuncionarioDto dto = new FuncionarioDto();
        dto.setIdFuncionario(funcionario.getId());
        return dto;
    }
}
