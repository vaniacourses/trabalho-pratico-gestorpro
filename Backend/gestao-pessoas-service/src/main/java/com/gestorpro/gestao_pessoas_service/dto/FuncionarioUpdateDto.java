package com.gestorpro.gestao_pessoas_service.dto;

import lombok.Data;

// DTO para receber os dados de atualização de um funcionário.
@Data
public class FuncionarioUpdateDto {
    private String nome;
    private String cargo;
    private String departamento;
    private String telefone;
    private String nivel;
}