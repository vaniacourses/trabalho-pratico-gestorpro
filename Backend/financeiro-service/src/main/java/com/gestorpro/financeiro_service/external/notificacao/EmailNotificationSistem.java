package com.gestorpro.financeiro_service.external.notificacao;

import org.springframework.stereotype.Service;

import com.gestorpro.financeiro_service.pessoa.model.entity.Pessoa;

@Service
public class EmailNotificationSistem implements INotificationSistem {
    @Override
    public void notificar(Pessoa pessoa, String assunto, String mensagem) {
        System.out.println("Mensagem enviada para " + pessoa.getEmail() + " com assunto: " + assunto);
    }
}
