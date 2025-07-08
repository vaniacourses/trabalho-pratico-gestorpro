package com.gestorpro.financeiro_service.despesa.model.mapper;

import com.gestorpro.financeiro_service.despesa.model.dto.DespesaRequestDTO;
import com.gestorpro.financeiro_service.despesa.model.dto.DespesaResponseDTO;
import com.gestorpro.financeiro_service.despesa.model.entity.Despesa;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DespesaMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "motivoRejeicao", ignore = true)
    Despesa toEntity(DespesaRequestDTO dto);

    DespesaResponseDTO toResponseDTO(Despesa entity);
}
