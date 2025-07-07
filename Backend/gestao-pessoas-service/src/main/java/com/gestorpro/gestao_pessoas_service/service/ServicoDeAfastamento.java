package com.gestorpro.gestao_pessoas_service.service;

import com.gestorpro.gestao_pessoas_service.model.*;
import com.gestorpro.gestao_pessoas_service.repository.FuncionarioRepository;
import com.gestorpro.gestao_pessoas_service.repository.SolicitacaoAfastamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicoDeAfastamento {

    @Autowired
    private SolicitacaoAfastamentoRepository solicitacaoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Transactional
    public SolicitacaoAfastamento solicitar(SolicitacaoAfastamento solicitacao) {
        // Valida se o funcionário associado existe
        funcionarioRepository.findById(solicitacao.getFuncionario().getIdFuncionario())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado!"));
        
        solicitacao.setStatus(StatusSolicitacao.PENDENTE);
        return solicitacaoRepository.save(solicitacao);
    }

    @Transactional
    public SolicitacaoAfastamento aprovar(Integer solicitacaoId) {
        SolicitacaoAfastamento solicitacao = solicitacaoRepository.findById(solicitacaoId)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada!"));
        
        solicitacao.setStatus(StatusSolicitacao.APROVADA);
        return solicitacaoRepository.save(solicitacao);
    }

    @Transactional
    public SolicitacaoAfastamento rejeitar(Integer solicitacaoId) {
        SolicitacaoAfastamento solicitacao = solicitacaoRepository.findById(solicitacaoId)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada!"));
        
        solicitacao.setStatus(StatusSolicitacao.REJEITADA);
        return solicitacaoRepository.save(solicitacao);
    }
    
    public List<SolicitacaoAfastamento> listarPorFuncionario(Integer idFuncionario) {
        return solicitacaoRepository.findByFuncionarioIdFuncionario(idFuncionario);
    }
}