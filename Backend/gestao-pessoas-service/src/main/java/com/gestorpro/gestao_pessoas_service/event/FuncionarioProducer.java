package com.gestorpro.gestao_pessoas_service.event;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import com.gestorpro.gestao_pessoas_service.dto.FuncionarioDto;

@Component
public class FuncionarioProducer {
    private final StreamBridge streamBridge; 

    public FuncionarioProducer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void sendFuncionarioCriado(FuncionarioDto dto) {
        streamBridge.send("funcionarioCriado-out-0", dto);
    }

    public void sendFuncionarioDeletado(int idFunc) {
        streamBridge.send("funcionarioDeletado-out-0", idFunc);
    }
}
