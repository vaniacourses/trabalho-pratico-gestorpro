package com.gestorpro.ti_service;

import com.gestorpro.ti_service.dto.AbrirChamadoRequest;
import com.gestorpro.ti_service.dto.ChamadoResponse;
import com.gestorpro.ti_service.service.ChamadoSuporteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootApplication
public class TIServiceApplication {
    static ChamadoSuporteService s = new ChamadoSuporteService();
    public static void main(String[] args) {
        SpringApplication.run(TIServiceApplication.class, args);
        Botar();
        Mostrar();
    }
    static void Botar() {
        AbrirChamadoRequest dto = new AbrirChamadoRequest("problema" , "urhente");
        s.abrirChamado(dto, "yanovaes@id.uff.br");
    }
    static void Mostrar(){
        List<ChamadoResponse> lista = s.listarTodosChamados();
        System.out.println(lista.toString());
    }
}
