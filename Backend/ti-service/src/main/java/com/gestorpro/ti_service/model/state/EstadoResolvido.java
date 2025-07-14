package com.gestorpro.ti_service.model.state;

import com.gestorpro.ti_service.model.ChamadoSuporte;
import com.gestorpro.ti_service.model.Status;
import com.gestorpro.ti_service.model.state.*;

public class EstadoResolvido implements EstadoChamado, PodeFechar, PodeReabrir {

    @Override
    public void fechar(ChamadoSuporte chamado) {
        chamado.setDataFechamento(java.time.LocalDateTime.now());
        chamado.setEstadoAtual(new EstadoFechado());
        chamado.setStatus(Status.FECHADO);
    }

    @Override
    public void reabrir(ChamadoSuporte chamado, String motivo) {
        chamado.setMotivoReabertura(motivo);
        chamado.setEstadoAtual(new EstadoAberto());
        chamado.setStatus(Status.ABERTO);
    }
}