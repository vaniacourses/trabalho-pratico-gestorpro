package com.gestorpro.financeiro_service.funcionario.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FuncionarioCreateDto {
    private String nome;
    private String cargo;
    private String departamento;
    private String telefone;

    // Dados para criar o usuário associado
    private String email;
    private String senha;
    
    // Dados para criar o contrato inicial
    private String tipoContrato;
    private Integer jornada;
    
    // Dados para o salário inicial (opcional)
    private BigDecimal salarioInicial;
}