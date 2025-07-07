package com.gestorpro.gestao_pessoas_service.service;

import com.gestorpro.gestao_pessoas_service.model.Funcionario;
import com.gestorpro.gestao_pessoas_service.model.RegistroPonto;
import com.gestorpro.gestao_pessoas_service.model.StatusPonto;
import com.gestorpro.gestao_pessoas_service.repository.FuncionarioRepository;
import com.gestorpro.gestao_pessoas_service.repository.RegistroPontoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ServicoDePonto {

    @Autowired
    private RegistroPontoRepository registroPontoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Transactional
    public RegistroPonto registrarEntrada(Integer idFuncionario) {
        // Valida se já não existe um registro de ponto aberto para hoje
        registroPontoRepository.findByFuncionarioIdFuncionarioAndData(idFuncionario, LocalDate.now())
            .ifPresent(r -> {
                if (r.getHoraSaida() == null) throw new IllegalStateException("Já existe um ponto de entrada registrado hoje sem saída.");
            });

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado."));

        RegistroPonto novoPonto = new RegistroPonto();
        novoPonto.setFuncionario(funcionario);
        novoPonto.setData(LocalDate.now());
        novoPonto.setHoraEntrada(LocalTime.now());
        novoPonto.setStatus(StatusPonto.PENDENTE);

        return registroPontoRepository.save(novoPonto);
    }

    @Transactional
    public RegistroPonto registrarSaida(Integer idFuncionario) {
        // Busca o registro do dia atual que não tenha hora de saída
        RegistroPonto pontoDoDia = registroPontoRepository.findByFuncionarioIdFuncionarioAndData(idFuncionario, LocalDate.now())
                .filter(r -> r.getHoraSaida() == null)
                .orElseThrow(() -> new IllegalStateException("Não há um registro de ponto de entrada aberto para registrar a saída."));

        pontoDoDia.setHoraSaida(LocalTime.now());
        return registroPontoRepository.save(pontoDoDia);
    }

    @Transactional
    public RegistroPonto validar(Integer idRegistro) {
        RegistroPonto registro = registroPontoRepository.findById(idRegistro)
                .orElseThrow(() -> new RuntimeException("Registro de ponto não encontrado."));
        registro.setStatus(StatusPonto.VALIDADO);
        return registroPontoRepository.save(registro);
    }
    
    public List<RegistroPonto> listarPorFuncionario(Integer idFuncionario) {
        return registroPontoRepository.findByFuncionarioIdFuncionarioOrderByDataDesc(idFuncionario);
    }
}