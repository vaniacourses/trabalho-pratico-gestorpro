package com.gestorpro.gestao_pessoas_service.service;

import com.gestorpro.gestao_pessoas_service.dto.SalarioDto;
import com.gestorpro.gestao_pessoas_service.dto.SalarioResponseDto; // Importe o novo DTO
import com.gestorpro.gestao_pessoas_service.model.Funcionario;
import com.gestorpro.gestao_pessoas_service.model.Salario;
import com.gestorpro.gestao_pessoas_service.repository.FuncionarioRepository;
import com.gestorpro.gestao_pessoas_service.repository.SalarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicoDeSalario {

    @Autowired
    private SalarioRepository salarioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Transactional
    public SalarioResponseDto registrarPagamento(SalarioDto salarioDto) {
        Funcionario funcionario = funcionarioRepository.findById(salarioDto.getIdFuncionario())
                .orElseThrow(() -> new RuntimeException("Funcionário com ID " + salarioDto.getIdFuncionario() + " não encontrado."));

        Salario novoSalario = new Salario();
        novoSalario.setValor(salarioDto.getValor());
        novoSalario.setDataPagamento(salarioDto.getDataPagamento());
        novoSalario.setFuncionario(funcionario);

        Salario salarioSalvo = salarioRepository.save(novoSalario);
        
        return paraDto(salarioSalvo); 
    }

    public List<SalarioResponseDto> listarHistoricoPorFuncionario(Integer idFuncionario) {
        List<Salario> historico = salarioRepository.findByFuncionarioIdFuncionarioOrderByDataPagamentoDesc(idFuncionario);
        
        return historico.stream()
                .map(this::paraDto)
                .collect(Collectors.toList());
    }
    
    // Método auxiliar para converter a entidade em DTO
    private SalarioResponseDto paraDto(Salario salario) {
        SalarioResponseDto dto = new SalarioResponseDto();
        dto.setIdSalario(salario.getIdSalario());
        dto.setValor(salario.getValor());
        dto.setDataPagamento(salario.getDataPagamento());
        if (salario.getFuncionario() != null) {
            dto.setIdFuncionario(salario.getFuncionario().getIdFuncionario());
        }
        return dto;
    }
}