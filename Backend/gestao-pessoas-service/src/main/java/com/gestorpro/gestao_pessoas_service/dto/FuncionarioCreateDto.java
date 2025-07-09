package com.gestorpro.gestao_pessoas_service.dto;

import lombok.Data;
import java.math.BigDecimal;

// DTO para receber os dados necessários para uma nova contratação.
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