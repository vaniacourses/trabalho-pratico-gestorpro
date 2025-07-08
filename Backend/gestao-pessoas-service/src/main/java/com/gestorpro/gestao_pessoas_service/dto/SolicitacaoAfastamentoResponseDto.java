package com.gestorpro.gestao_pessoas_service.dto;

import com.gestorpro.gestao_pessoas_service.model.StatusSolicitacao;
import com.gestorpro.gestao_pessoas_service.model.TipoSolicitacao;
import lombok.Data;
import java.time.LocalDate;

@Data
public class SolicitacaoAfastamentoResponseDto {

    private Integer idSolicitacao;
    private TipoSolicitacao tipo;
    private StatusSolicitacao status;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String justificativa;
    private Integer idFuncionario;
}