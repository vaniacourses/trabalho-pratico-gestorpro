package com.gestorpro.gestao_pessoas_service.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BeneficioDto {
    private String nome;
    private String descricao;
    private BigDecimal valor;
    private Integer idFuncionario;
}