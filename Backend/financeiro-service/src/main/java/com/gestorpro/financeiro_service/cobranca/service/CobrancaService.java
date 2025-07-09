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
import com.gestorpro.financeiro_service.pessoa.model.entity.Pessoa;
import com.gestorpro.financeiro_service.pessoa.repository.PessoaRepository;

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
    private final PessoaRepository pessoaRepository;

    public CobrancaService(CobrancaMapper mapper, CobrancaRepository repository, INotificationSistem notifier, IBankSistem bank, PessoaRepository pessoaRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.notifier = notifier;
        this.bank = bank;
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public CobrancaResponseDTO agendarCobranca(CobrancaRequestDTO dto) {
        Pessoa devedor = new Pessoa();
        devedor.setEmail(dto.getEmailDevedor());
        devedor.setTelefone(dto.getTelefone());

        pessoaRepository.save(devedor);

        Cobranca cobranca = mapper.toEntity(dto);
        cobranca.setDevedor(devedor);
        cobranca.setStatus(CobrancaStatus.PENDENTE);
        cobranca.setDataEmissao(LocalDate.now());
        
        Cobranca savedCobranca = repository.save(cobranca);
        
        try {
            bank.registrarCobranca(savedCobranca);
        } catch(Exception e) {
            throw new ErroComunicacaoBancoException(e.getMessage());
        }

        notifier.notificar(devedor, "Cobrança", "Cobrança agendada!");

        return mapper.toResponseDTO(savedCobranca);
    }

    @Transactional
    public CobrancaResponseDTO cancelarCobranca(int id) {
        Cobranca cobranca = repository.findById(id)
            .orElseThrow(() -> new CobrancaNaoEncontradaException(id));

        if(cobranca.getStatus() == CobrancaStatus.PAGA) {
            throw new CobrancaPagaException(id);
        }

        cobranca.cancelar();
        Cobranca updatedCobranca = repository.save(cobranca);

        notifier.notificar(updatedCobranca.getDevedor(), "Cobrança", "Cobrança cancelada!");
        
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
        
        cobranca.receber();
        Cobranca updatedCobranca = repository.save(cobranca);

        notifier.notificar(updatedCobranca.getDevedor(), "Cobrança", "Cobrança paga!");
        
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
