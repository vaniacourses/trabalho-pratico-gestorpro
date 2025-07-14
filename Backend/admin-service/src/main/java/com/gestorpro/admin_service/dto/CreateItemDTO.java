package com.gestorpro.admin_service.dto;

import java.time.LocalDate;

public record CreateItemDTO (
    String tipo,
    String nome,
    String descricao,
    LocalDate ultimaManutencao,
    int quantidade
){

}
