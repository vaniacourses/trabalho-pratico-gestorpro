package com.gestorpro.gestao_pessoas_service.service;

import com.gestorpro.gestao_pessoas_service.dto.BeneficioDto;
import com.gestorpro.gestao_pessoas_service.model.Beneficio;
import com.gestorpro.gestao_pessoas_service.model.Funcionario;
import com.gestorpro.gestao_pessoas_service.repository.BeneficioRepository;
import com.gestorpro.gestao_pessoas_service.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicoDeBeneficios {

    @Autowired
    private BeneficioRepository beneficioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Transactional
    public Beneficio concederBeneficio(BeneficioDto beneficioDto) {
        // 1. Busca o funcionário pelo ID informado no DTO
        Funcionario funcionario = funcionarioRepository.findById(beneficioDto.getIdFuncionario())
                .orElseThrow(() -> new RuntimeException("Funcionário com ID " + beneficioDto.getIdFuncionario() + " não encontrado."));

        // 2. Cria uma nova entidade Beneficio a partir dos dados do DTO
        Beneficio novoBeneficio = new Beneficio();
        novoBeneficio.setNome(beneficioDto.getNome());
        novoBeneficio.setDescricao(beneficioDto.getDescricao());
        novoBeneficio.setValor(beneficioDto.getValor());
        novoBeneficio.setFuncionario(funcionario); // Associa o funcionário encontrado

        // 3. Salva o novo benefício no banco de dados
        return beneficioRepository.save(novoBeneficio);
    }

    public List<Beneficio> listarPorFuncionario(Integer idFuncionario) {
        return beneficioRepository.findByFuncionarioIdFuncionario(idFuncionario);
    }

    @Transactional
    public void revogarBeneficio(Integer idBeneficio) {
        if (!beneficioRepository.existsById(idBeneficio)) {
            throw new RuntimeException("Benefício com ID " + idBeneficio + " não encontrado.");
        }
        beneficioRepository.deleteById(idBeneficio);
    }
}