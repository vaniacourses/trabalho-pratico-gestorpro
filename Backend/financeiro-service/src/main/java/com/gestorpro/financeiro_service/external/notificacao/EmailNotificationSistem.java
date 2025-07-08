package com.gestorpro.financeiro_service.external.notificacao;

import org.springframework.stereotype.Service;

@Service
public class EmailNotificationSistem implements INotificationSistem {
    @Override
    public void notificar(String email, String assunto, String mensagem) {
        System.out.println("Mensagem enviada para " + email + " com assunto: " + assunto);
    }
}
