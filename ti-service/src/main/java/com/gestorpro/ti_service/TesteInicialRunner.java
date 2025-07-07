package com.gestorpro.ti_service;

import com.gestorpro.ti_service.dto.AbrirChamadoRequest;
import com.gestorpro.ti_service.dto.ChamadoResponse;
import com.gestorpro.ti_service.service.ChamadoSuporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component // Marca esta classe como um componente gerenciado pelo Spring
public class TesteInicialRunner implements CommandLineRunner {

    // O Spring vai INJETAR uma instância TOTALMENTE FUNCIONAL do serviço aqui.
    // Este serviço terá seu repositório e tudo mais conectado.
    @Autowired
    private ChamadoSuporteService chamadoService;

    /**
     * Este método será executado automaticamente pelo Spring DEPOIS que
     * a aplicação estiver totalmente iniciada e pronta.
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- EXECUTANDO TESTE INICIAL ---");

        // Lógica do seu método Botar()
        System.out.println("Abrindo um novo chamado...");
        AbrirChamadoRequest dto = new AbrirChamadoRequest("Problema no Login", "Urgente");
        chamadoService.abrirChamado(dto, "yanovaes@id.uff.br");
        System.out.println("Chamado aberto com sucesso!");

        // Lógica do seu método Mostrar()
        System.out.println("\nListando todos os chamados:");
        List<ChamadoResponse> lista = chamadoService.listarTodosChamados();
        if (lista.isEmpty()) {
            System.out.println("Nenhum chamado encontrado.");
        } else {
            lista.forEach(chamado -> System.out.println(chamado.toString()));
        }

        System.out.println("--- TESTE INICIAL CONCLUÍDO ---");
    }
}