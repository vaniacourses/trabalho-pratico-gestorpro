package com.gestorpro.gestao_projetos_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestorpro.gestao_projetos_service.dto.ProjetoDto;
import com.gestorpro.gestao_projetos_service.model.Projeto;
import com.gestorpro.gestao_projetos_service.repository.ProjetoRepository;

@Service
public class ProjetoService {
    @Autowired
    private ProjetoRepository projetoRepository;

    public Projeto createProjeto(ProjetoDto proj){
        Projeto projeto = Projeto.builder()
                            .cliente(proj.getCliente())
                            .dataInicio(proj.getDataInicio())
                            .dataTermino(proj.getDataTermino())
                            .tipo(proj.getTipo())
                            .status(proj.getStatus())
                            .build();
        return projetoRepository.save(projeto);
    }
}
