package com.gestorpro.ti_service.model.state;

import com.gestorpro.ti_service.model.ChamadoSuporte;
import com.gestorpro.ti_service.model.Status; // Enum para persistência

// Implementa apenas o que um chamado Aberto pode fazer
public class EstadoAberto implements EstadoChamado, PodeIniciar, PodeCancelar {

    @Override
    public void iniciarAtendimento(ChamadoSuporte chamado) {
        chamado.setEstadoAtual(new EstadoEmAndamento());
        chamado.setStatus(Status.EM_ANDAMENTO);
    }

    @Override
    public void cancelar(ChamadoSuporte chamado, String motivo) {
        chamado.setMotivoCancelamento(motivo);
        chamado.setEstadoAtual(new EstadoCancelado());
        chamado.setStatus(Status.CANCELADO);
    }
}