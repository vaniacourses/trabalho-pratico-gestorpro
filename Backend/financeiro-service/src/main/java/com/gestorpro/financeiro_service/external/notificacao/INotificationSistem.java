package com.gestorpro.financeiro_service.external.notificacao;

public interface INotificationSistem {
    void notificar(String email, String assunto, String mensagem);
}
