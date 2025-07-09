package com.gestorpro.financeiro_service.external.notificacao;

import com.gestorpro.financeiro_service.pessoa.model.entity.Pessoa;

public interface INotificationSistem {
    void notificar(Pessoa pessoa, String assunto, String mensagem);
}
