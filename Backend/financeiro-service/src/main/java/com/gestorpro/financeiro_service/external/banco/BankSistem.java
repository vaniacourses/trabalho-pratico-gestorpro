package com.gestorpro.financeiro_service.external.banco;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.gestorpro.financeiro_service.cobranca.model.entity.Cobranca;

@Service
public class BankSistem implements IBankSistem {
    private final Random random = new Random();

    @Override
    public void registrarCobranca(Cobranca cobranca) {
        boolean falha = random.nextBoolean();
        // if (falha) {
        //     throw new RuntimeException("Sistema de banco fora de ar.");
        // }
        System.out.println("Cobrança " + cobranca.getId() + " enviada ao banco!");
    }
}
