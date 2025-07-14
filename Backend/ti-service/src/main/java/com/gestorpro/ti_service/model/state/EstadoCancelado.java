package com.gestorpro.ti_service.model.state;

import com.gestorpro.ti_service.model.ChamadoSuporte;
import com.gestorpro.ti_service.model.Status;

public class EstadoCancelado implements EstadoChamado, PodeFechar, PodeReabrir {

    @Override
    public void fechar(ChamadoSuporte chamado) {
        chamado.setDataFechamento(java.time.LocalDateTime.now());
        chamado.setEstadoAtual(new EstadoFechado());
        chamado.setStatus(Status.FECHADO);
    }

    @Override
    public void reabrir(ChamadoSuporte chamado, String motivo) {
        System.out.println("Chamado #" + chamado.getId() + " reaberto. Motivo: " + motivo + ". Mudando para o estado ABERTO.");
        chamado.setMotivoReabertura(motivo);
        chamado.setEstadoAtual(new EstadoAberto());
        chamado.setStatus(Status.ABERTO);
    }
}