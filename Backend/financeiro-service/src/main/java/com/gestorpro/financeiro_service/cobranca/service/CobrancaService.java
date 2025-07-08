package com.gestorpro.financeiro_service.cobranca.service;

import com.gestorpro.financeiro_service.cobranca.exception.CobrancaCanceladaException;
import com.gestorpro.financeiro_service.cobranca.exception.CobrancaNaoEncontradaException;
import com.gestorpro.financeiro_service.cobranca.exception.CobrancaPagaException;
import com.gestorpro.financeiro_service.cobranca.exception.ErroComunicacaoBancoException;
import com.gestorpro.financeiro_service.cobranca.model.dto.CobrancaRequestDTO;
import com.gestorpro.financeiro_service.cobranca.model.dto.CobrancaResponseDTO;
import com.gestorpro.financeiro_service.cobranca.model.entity.Cobranca;
import com.gestorpro.financeiro_service.cobranca.model.enums.CobrancaStatus;
import com.gestorpro.financeiro_service.cobranca.model.mapper.CobrancaMapper;
import com.gestorpro.financeiro_service.cobranca.repository.CobrancaRepository;
import com.gestorpro.financeiro_service.external.banco.IBankSistem;
import com.gestorpro.financeiro_service.external.notificacao.INotificationSistem;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.List;
import java.time.LocalDate;

@Service
public class CobrancaService {
    private final CobrancaMapper mapper;
    private final CobrancaRepository repository;
    private final INotificationSistem notifier;
    private final IBankSistem bank;

    public CobrancaService(CobrancaMapper mapper, CobrancaRepository repository, INotificationSistem notifier, IBankSistem bank) {
        this.mapper = mapper;
        this.repository = repository;
        this.notifier = notifier;
        this.bank = bank;
    }

    @Transactional
    public CobrancaResponseDTO agendarCobranca(CobrancaRequestDTO dto) {
        Cobranca cobranca = mapper.toEntity(dto);
        
        cobranca.setStatus(CobrancaStatus.PENDENTE);
        cobranca.setDataEmissao(LocalDate.now());
        Cobranca savedCobranca = repository.save(cobranca);
        
        try {
            bank.registrarCobranca(savedCobranca);
        } catch(Exception e) {
            throw new ErroComunicacaoBancoException(e.getMessage());
        }

        notifier.notificar(savedCobranca.getEmailNotificacao(), "Cobrança", "Cobrança agendada!");

        return mapper.toResponseDTO(savedCobranca);
    }

    @Transactional
    public CobrancaResponseDTO cancelarCobranca(int id) {
        Cobranca cobranca = repository.findById(id)
            .orElseThrow(() -> new CobrancaNaoEncontradaException(id));

        if(cobranca.getStatus() == CobrancaStatus.PAGA) {
            throw new CobrancaPagaException(id);
        }

        cobranca.atualizarStatus(CobrancaStatus.CANCELADA);
        Cobranca updatedCobranca = repository.save(cobranca);
        
        return mapper.toResponseDTO(updatedCobranca);
    }

    @Transactional
    public CobrancaResponseDTO receberCobranca(int id) {
        Cobranca cobranca = repository.findById(id)
            .orElseThrow(() -> new CobrancaNaoEncontradaException(id));

        if(cobranca.getStatus() == CobrancaStatus.CANCELADA) {
            throw new CobrancaCanceladaException(id);
        }
        if(cobranca.getStatus() == CobrancaStatus.PAGA) {
            throw new CobrancaPagaException(id);
        }
        
        cobranca.atualizarStatus(CobrancaStatus.PAGA);
        Cobranca updatedCobranca = repository.save(cobranca);
        
        return mapper.toResponseDTO(updatedCobranca);
    }

    @Transactional(readOnly = true)
    public CobrancaResponseDTO getCobranca(int id) {
        Cobranca cobranca = repository.findById(id)
            .orElseThrow(() -> new CobrancaNaoEncontradaException(id));

        return mapper.toResponseDTO(cobranca);
    }

    @Transactional(readOnly = true)
    public List<CobrancaResponseDTO> listarCobrancas() {
        return repository.findAll()
            .stream()
            .map(cobranca -> mapper.toResponseDTO(cobranca))
            .collect(Collectors.toList());
    }
}
