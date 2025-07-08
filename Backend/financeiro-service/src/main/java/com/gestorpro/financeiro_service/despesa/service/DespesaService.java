package com.gestorpro.financeiro_service.despesa.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestorpro.financeiro_service.despesa.exception.DespesaNaoEncontradaException;
import com.gestorpro.financeiro_service.despesa.exception.DespesaProcessadaException;
import com.gestorpro.financeiro_service.despesa.model.dto.DespesaRejectedDTO;
import com.gestorpro.financeiro_service.despesa.model.dto.DespesaRequestDTO;
import com.gestorpro.financeiro_service.despesa.model.dto.DespesaResponseDTO;
import com.gestorpro.financeiro_service.despesa.model.entity.Despesa;
import com.gestorpro.financeiro_service.despesa.model.enums.DespesaStatus;
import com.gestorpro.financeiro_service.despesa.model.mapper.DespesaMapper;
import com.gestorpro.financeiro_service.despesa.repository.DespesaRepository;
import com.gestorpro.financeiro_service.external.notificacao.INotificationSistem;

@Service
public class DespesaService {
    private final DespesaRepository repository;
    private final INotificationSistem notifier;
    private final DespesaMapper mapper;

    public DespesaService(DespesaRepository repository, INotificationSistem notifier, DespesaMapper mapper) {
        this.repository = repository;
        this.notifier = notifier;
        this.mapper = mapper;
    }

    @Transactional
    public DespesaResponseDTO criarSolicitacao(DespesaRequestDTO dto) {
        Despesa solicitacao = mapper.toEntity(dto);
        
        solicitacao.setStatus(DespesaStatus.PENDENTE);
        solicitacao.setMotivoRejeicao("");

        return mapper.toResponseDTO(repository.save(solicitacao));
    }

    @Transactional
    public DespesaResponseDTO reprovarSolicitacao(int id, DespesaRejectedDTO dto) {
        Despesa solicitacao = repository.findById(id)
            .orElseThrow(() -> new DespesaNaoEncontradaException(id));

        if(solicitacao.getStatus() != DespesaStatus.PENDENTE) {
            throw new DespesaProcessadaException(id);
        }
        
        solicitacao.setStatus(DespesaStatus.REJEITADA);
        solicitacao.setMotivoRejeicao(dto.getJustificativa());
        // notifier.notificar("algum", "Solicitação de despesa rejeitada", "A sua solicitação foi rejeitada");

        return mapper.toResponseDTO(repository.save(solicitacao));
    }

    @Transactional
    public DespesaResponseDTO aprovarSolicitacao(int id) {
        Despesa solicitacao = repository.findById(id)
            .orElseThrow(() -> new DespesaNaoEncontradaException(id));

        if(solicitacao.getStatus() != DespesaStatus.PENDENTE) {
            throw new DespesaProcessadaException(id);
        }
        
        solicitacao.setStatus(DespesaStatus.APROVADA);
        // notifier.notificar("algum", "Solicitação de despesa aprovada", "A sua solicitação foi aprovada");

        return mapper.toResponseDTO(repository.save(solicitacao));
    }

    @Transactional
    public DespesaResponseDTO getSolicitacao(int id) {
        Despesa solicitacao = repository.findById(id)
            .orElseThrow(() -> new DespesaNaoEncontradaException(id));

        return mapper.toResponseDTO(solicitacao);
    }

    @Transactional(readOnly = true)
    public List<DespesaResponseDTO> listarSolicitacoesPendentes() {
        return repository.findByStatus(DespesaStatus.PENDENTE)
            .stream()
            .map(solicitacao -> mapper.toResponseDTO(solicitacao))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DespesaResponseDTO> listarSolicitacoes() {
        return repository.findAll()
            .stream()
            .map(solicitacao -> mapper.toResponseDTO(solicitacao))
            .collect(Collectors.toList());
    }
}
