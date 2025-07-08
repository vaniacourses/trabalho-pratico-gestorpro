package com.gestorpro.ti_service.model;

import com.gestorpro.ti_service.model.state.*;
import java.util.function.Supplier;

public enum Status {
    ABERTO(EstadoAberto::new),
    EM_ANDAMENTO(EstadoEmAndamento::new),
    RESOLVIDO(EstadoResolvido::new),
    FECHADO(EstadoFechado::new),
    CANCELADO(EstadoCancelado::new);

    // Cada enum constante guarda um "fornecedor" da sua respectiva instância de estado.
    // Usamos uma referência de método (::new) para isso, que é super eficiente.
    private final Supplier<EstadoChamado> estadoSupplier;

    Status(Supplier<EstadoChamado> supplier) {
        this.estadoSupplier = supplier;
    }

    // Este método age como a nossa fábrica.
    public EstadoChamado obterInstancia() {
        return estadoSupplier.get();
    }
}