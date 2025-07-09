package com.gestorpro.gestao_pessoas_service.service;

import com.gestorpro.gestao_pessoas_service.dto.BeneficioDto;
import com.gestorpro.gestao_pessoas_service.dto.BeneficioResponseDto;
import com.gestorpro.gestao_pessoas_service.model.Beneficio;
import com.gestorpro.gestao_pessoas_service.model.Funcionario;
import com.gestorpro.gestao_pessoas_service.repository.BeneficioRepository;
import com.gestorpro.gestao_pessoas_service.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicoDeBeneficios {

    @Autowired
    private BeneficioRepository beneficioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Transactional
    public BeneficioResponseDto concederBeneficio(BeneficioDto beneficioDto) {
        Funcionario funcionario = funcionarioRepository.findById(beneficioDto.getIdFuncionario())
                .orElseThrow(() -> new RuntimeException("Funcionário com ID " + beneficioDto.getIdFuncionario() + " não encontrado."));

        Beneficio novoBeneficio = new Beneficio();
        novoBeneficio.setNome(beneficioDto.getNome());
        novoBeneficio.setDescricao(beneficioDto.getDescricao());
        novoBeneficio.setValor(beneficioDto.getValor());
        novoBeneficio.setFuncionario(funcionario);

        Beneficio beneficioSalvo = beneficioRepository.save(novoBeneficio);

        return paraDto(beneficioSalvo);
    }

    public List<BeneficioResponseDto> listarPorFuncionario(Integer idFuncionario) {
        List<Beneficio> beneficios = beneficioRepository.findByFuncionarioIdFuncionario(idFuncionario);
        return beneficios.stream()
                .map(this::paraDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void revogarBeneficio(Integer idBeneficio) {
        if (!beneficioRepository.existsById(idBeneficio)) {
            throw new RuntimeException("Benefício com ID " + idBeneficio + " não encontrado.");
        }
        beneficioRepository.deleteById(idBeneficio);
    }

    private BeneficioResponseDto paraDto(Beneficio beneficio) {
        BeneficioResponseDto dto = new BeneficioResponseDto();
        dto.setIdBeneficio(beneficio.getIdBeneficio());
        dto.setNome(beneficio.getNome());
        dto.setDescricao(beneficio.getDescricao());
        dto.setValor(beneficio.getValor());
        if (beneficio.getFuncionario() != null) {
            dto.setIdFuncionario(beneficio.getFuncionario().getIdFuncionario());
        }
        return dto;
    }
}