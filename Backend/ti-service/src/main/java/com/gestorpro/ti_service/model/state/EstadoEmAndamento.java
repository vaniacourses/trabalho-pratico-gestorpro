package com.gestorpro.ti_service.model.state;

import com.gestorpro.ti_service.model.ChamadoSuporte;
import com.gestorpro.ti_service.model.Status;

public class EstadoEmAndamento implements EstadoChamado, PodeResolver, PodeCancelar {

    @Override
    public void resolver(ChamadoSuporte chamado, String solucao) {
        System.out.println("Chamado #" + chamado.getId() + " resolvido. Mudando para o estado RESOLVIDO.");
        chamado.setSolucao(solucao); // Assumindo que a entidade tem este campo
        chamado.setEstadoAtual(new EstadoResolvido());
        chamado.setStatus(Status.RESOLVIDO);
    }

    @Override
    public void cancelar(ChamadoSuporte chamado, String motivo) {
        System.out.println("Chamado #" + chamado.getId() + " cancelado durante o atendimento. Mudando para o estado CANCELADO.");
        chamado.setMotivoCancelamento(motivo);
        chamado.setEstadoAtual(new EstadoCancelado());
        chamado.setStatus(Status.CANCELADO);
    }
}