package com.gestorpro.gestao_pessoas_service.service;

import com.gestorpro.gestao_pessoas_service.dto.ContratoDto;
import com.gestorpro.gestao_pessoas_service.model.Contrato;
import com.gestorpro.gestao_pessoas_service.model.Funcionario;
import com.gestorpro.gestao_pessoas_service.repository.ContratoRepository;
import com.gestorpro.gestao_pessoas_service.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicoDeContrato {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Transactional
    public Contrato criarContrato(ContratoDto contratoDto) {
        // 1. Valida e busca o funcionário
        Funcionario funcionario = funcionarioRepository.findById(contratoDto.getIdFuncionario())
                .orElseThrow(() -> new RuntimeException("Funcionário com ID " + contratoDto.getIdFuncionario() + " não encontrado."));

        // 2. Cria a nova entidade Contrato
        Contrato novoContrato = new Contrato();
        novoContrato.setTipo(contratoDto.getTipo());
        novoContrato.setJornada(contratoDto.getJornada());
        novoContrato.setFuncionario(funcionario);

        // 3. Salva e retorna o novo contrato
        return contratoRepository.save(novoContrato);
    }

    public List<Contrato> listarPorFuncionario(Integer idFuncionario) {
        return contratoRepository.findByFuncionarioIdFuncionario(idFuncionario);
    }

    @Transactional
    public Contrato atualizarContrato(Integer idContrato, ContratoDto contratoDto) {
        Contrato contratoExistente = contratoRepository.findById(idContrato)
                .orElseThrow(() -> new RuntimeException("Contrato com ID " + idContrato + " não encontrado."));
        
        contratoExistente.setTipo(contratoDto.getTipo());
        contratoExistente.setJornada(contratoDto.getJornada());
        
        return contratoRepository.save(contratoExistente);
    }

    @Transactional
    public void rescindirContrato(Integer idContrato) {
        if (!contratoRepository.existsById(idContrato)) {
            throw new RuntimeException("Contrato com ID " + idContrato + " não encontrado.");
        }
        contratoRepository.deleteById(idContrato);
    }
}