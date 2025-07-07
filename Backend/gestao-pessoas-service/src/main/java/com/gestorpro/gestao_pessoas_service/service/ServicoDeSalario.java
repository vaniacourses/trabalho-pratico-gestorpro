package com.gestorpro.gestao_pessoas_service.service;

import com.gestorpro.gestao_pessoas_service.dto.SalarioDto;
import com.gestorpro.gestao_pessoas_service.model.Funcionario;
import com.gestorpro.gestao_pessoas_service.model.Salario;
import com.gestorpro.gestao_pessoas_service.repository.FuncionarioRepository;
import com.gestorpro.gestao_pessoas_service.repository.SalarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicoDeSalario {

    @Autowired
    private SalarioRepository salarioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Transactional
    public Salario registrarPagamento(SalarioDto salarioDto) {
        // 1. Busca o funcionário para associar o pagamento
        Funcionario funcionario = funcionarioRepository.findById(salarioDto.getIdFuncionario())
                .orElseThrow(() -> new RuntimeException("Funcionário com ID " + salarioDto.getIdFuncionario() + " não encontrado."));

        // 2. Cria a nova entidade Salario
        Salario novoSalario = new Salario();
        novoSalario.setValor(salarioDto.getValor());
        novoSalario.setDataPagamento(salarioDto.getDataPagamento());
        novoSalario.setFuncionario(funcionario);

        // 3. Salva o registro no banco
        return salarioRepository.save(novoSalario);
    }

    public List<Salario> listarHistoricoPorFuncionario(Integer idFuncionario) {
        return salarioRepository.findByFuncionarioIdFuncionarioOrderByDataPagamentoDesc(idFuncionario);
    }
}