package com.gestorpro.gestao_pessoas_service.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SalarioResponseDto {
    private Integer idSalario;
    private BigDecimal valor;
    private LocalDate dataPagamento;
    private Integer idFuncionario;
}