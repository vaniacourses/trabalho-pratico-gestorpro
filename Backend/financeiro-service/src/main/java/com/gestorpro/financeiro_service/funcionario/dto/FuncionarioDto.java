package com.gestorpro.financeiro_service.funcionario.dto;

import lombok.Data;
import java.time.LocalDate;

// DTO para retornar dados de um funcionário, evitando expor a senha e outras entidades complexas.
@Data
public class FuncionarioDto {
    private Integer idFuncionario;
    private String nome;
    private String cargo;
    private String departamento;
    private String email; // Extraído do objeto Usuario
    private LocalDate dataAdmissao;
}