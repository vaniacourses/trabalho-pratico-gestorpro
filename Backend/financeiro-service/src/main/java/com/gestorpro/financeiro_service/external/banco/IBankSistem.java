package com.gestorpro.financeiro_service.external.banco;

import com.gestorpro.financeiro_service.cobranca.model.entity.Cobranca;

public interface IBankSistem {
    void registrarCobranca(Cobranca cobranca);
}
