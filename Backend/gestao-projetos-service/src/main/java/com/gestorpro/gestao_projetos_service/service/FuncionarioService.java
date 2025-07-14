package com.gestorpro.gestao_projetos_service.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestorpro.gestao_projetos_service.dto.FuncionarioProjetoDto;
import com.gestorpro.gestao_projetos_service.dto.ProjetoDto;
import com.gestorpro.gestao_projetos_service.dto.ProjetoFuncionarioDto;
import com.gestorpro.gestao_projetos_service.model.Funcionario;
import com.gestorpro.gestao_projetos_service.model.Projeto;
import com.gestorpro.gestao_projetos_service.repository.FuncionarioRepository;
import com.gestorpro.gestao_projetos_service.repository.ProjetoRepository;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ProjetoRepository projetoRepository; 

     public Funcionario adicionarProjetoAoFuncionario(Long funcionarioId, Long projetoId) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        funcionario.getProjetos().add(projeto);
        return funcionarioRepository.save(funcionario);
    }

    public Set<ProjetoFuncionarioDto> listarProjetosDoFuncionario(Integer funcionarioId) {
        Funcionario funcionario = funcionarioRepository.findByIdWithProjetos(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        
        Set<ProjetoFuncionarioDto> projetosDto = funcionario.getProjetos().stream()
            .map(projeto -> {
                ProjetoFuncionarioDto projetoDto = new ProjetoFuncionarioDto();
                projetoDto.setId(projeto.getId());
                projetoDto.setTipo(projeto.getTipo());
                projetoDto.setCliente(projeto.getCliente());
                projetoDto.setStatus(projeto.getStatus());
                projetoDto.setDataInicio(projeto.getDataInicio());
                projetoDto.setDataTermino(projeto.getDataTermino());
                return projetoDto;
            }).collect(Collectors.toSet());

        return projetosDto;
    }
}
