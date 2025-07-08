package com.gestorpro.financeiro_service.config;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gestorpro.financeiro_service.funcionario.dto.FuncionarioDto;

@Configuration
public class FuncionarioConsumer {
    @Bean
    public Consumer<FuncionarioDto> funcionarioCriado() {
        return funcionario -> {
            System.out.println("OI");
        };
    }
}
