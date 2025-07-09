package com.gestorpro.financeiro_service.cobranca.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gestorpro.financeiro_service.cobranca.model.dto.CobrancaRequestDTO;
import com.gestorpro.financeiro_service.cobranca.model.dto.CobrancaResponseDTO;
import com.gestorpro.financeiro_service.cobranca.model.entity.Cobranca;

@Mapper(componentModel = "spring")
public interface CobrancaMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataEmissao", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "devedor", ignore = true)
    Cobranca toEntity(CobrancaRequestDTO dto);

    @Mapping(source = "devedor.email", target = "emailDevedor")
    @Mapping(source = "devedor.telefone", target = "telefoneDevedor")
    CobrancaResponseDTO toResponseDTO(Cobranca entity);
}
