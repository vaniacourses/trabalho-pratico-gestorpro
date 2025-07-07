package com.gestorpro.gestao_pessoas_service.service;

import com.gestorpro.gestao_pessoas_service.dto.SolicitacaoAfastamentoResponseDto;
import com.gestorpro.gestao_pessoas_service.model.*;
import com.gestorpro.gestao_pessoas_service.repository.FuncionarioRepository;
import com.gestorpro.gestao_pessoas_service.repository.SolicitacaoAfastamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicoDeAfastamento {

    @Autowired
    private SolicitacaoAfastamentoRepository solicitacaoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    // O método de criação também deve retornar um DTO para consistência
    @Transactional
    public SolicitacaoAfastamentoResponseDto solicitar(SolicitacaoAfastamento solicitacao) {
        funcionarioRepository.findById(solicitacao.getFuncionario().getIdFuncionario())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado!"));
        
        solicitacao.setStatus(StatusSolicitacao.PENDENTE);
        SolicitacaoAfastamento solicitacaoSalva = solicitacaoRepository.save(solicitacao);
        return paraDto(solicitacaoSalva);
    }

    // Método de aprovação agora retorna DTO
    @Transactional
    public SolicitacaoAfastamentoResponseDto aprovar(Integer solicitacaoId) {
        SolicitacaoAfastamento solicitacao = solicitacaoRepository.findById(solicitacaoId)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada!"));
        
        solicitacao.setStatus(StatusSolicitacao.APROVADA);
        SolicitacaoAfastamento solicitacaoSalva = solicitacaoRepository.save(solicitacao);
        return paraDto(solicitacaoSalva);
    }

    // Método de rejeição agora retorna DTO
    @Transactional
    public SolicitacaoAfastamentoResponseDto rejeitar(Integer solicitacaoId) {
        SolicitacaoAfastamento solicitacao = solicitacaoRepository.findById(solicitacaoId)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada!"));
        
        solicitacao.setStatus(StatusSolicitacao.REJEITADA);
        SolicitacaoAfastamento solicitacaoSalva = solicitacaoRepository.save(solicitacao);
        return paraDto(solicitacaoSalva);
    }
    
    // Método de listagem agora retorna uma lista de DTOs
    public List<SolicitacaoAfastamentoResponseDto> listarPorFuncionario(Integer idFuncionario) {
        List<SolicitacaoAfastamento> solicitacoes = solicitacaoRepository.findByFuncionarioIdFuncionario(idFuncionario);
        return solicitacoes.stream()
                .map(this::paraDto)
                .collect(Collectors.toList());
    }
    
    // Método auxiliar privado para a conversão
    private SolicitacaoAfastamentoResponseDto paraDto(SolicitacaoAfastamento solicitacao) {
        SolicitacaoAfastamentoResponseDto dto = new SolicitacaoAfastamentoResponseDto();
        dto.setIdSolicitacao(solicitacao.getIdSolicitacao());
        dto.setTipo(solicitacao.getTipo());
        dto.setStatus(solicitacao.getStatus());
        dto.setDataInicio(solicitacao.getDataInicio());
        dto.setDataFim(solicitacao.getDataFim());
        dto.setJustificativa(solicitacao.getJustificativa());
        if (solicitacao.getFuncionario() != null) {
            dto.setIdFuncionario(solicitacao.getFuncionario().getIdFuncionario());
        }
        return dto;
    }
}