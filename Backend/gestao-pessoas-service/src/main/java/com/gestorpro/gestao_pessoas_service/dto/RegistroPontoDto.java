package com.gestorpro.gestao_pessoas_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistroPontoDto {

    @NotNull(message = "O ID do funcionário não pode ser nulo.")
    private Integer idFuncionario;

}