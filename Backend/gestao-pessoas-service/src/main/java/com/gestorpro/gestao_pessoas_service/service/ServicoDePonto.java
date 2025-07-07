package com.gestorpro.gestao_pessoas_service.service;

import com.gestorpro.gestao_pessoas_service.dto.RegistroPontoResponseDto;
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
import java.util.stream.Collectors;

@Service
public class ServicoDePonto {

    @Autowired
    private RegistroPontoRepository registroPontoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Transactional
    public RegistroPontoResponseDto registrarEntrada(Integer idFuncionario) {
        registroPontoRepository.findByFuncionarioIdFuncionarioAndData(idFuncionario, LocalDate.now())
                .filter(r -> r.getHoraSaida() == null)
                .ifPresent(r -> {
                    throw new IllegalStateException("Já existe um ponto de entrada registrado hoje sem saída.");
                });

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado."));

        RegistroPonto novoPonto = new RegistroPonto();
        novoPonto.setFuncionario(funcionario);
        novoPonto.setData(LocalDate.now());
        novoPonto.setHoraEntrada(LocalTime.now());
        novoPonto.setStatus(StatusPonto.PENDENTE);

        return paraDto(registroPontoRepository.save(novoPonto));
    }

    @Transactional
    public RegistroPontoResponseDto registrarSaida(Integer idFuncionario) {
        RegistroPonto pontoDoDia = registroPontoRepository.findByFuncionarioIdFuncionarioAndData(idFuncionario, LocalDate.now())
                .filter(r -> r.getHoraSaida() == null)
                .orElseThrow(() -> new IllegalStateException("Não há um registro de ponto de entrada aberto para registrar a saída."));

        pontoDoDia.setHoraSaida(LocalTime.now());
        return paraDto(registroPontoRepository.save(pontoDoDia));
    }

    @Transactional
    public RegistroPontoResponseDto validar(Integer idRegistro) {
        RegistroPonto registro = registroPontoRepository.findById(idRegistro)
                .orElseThrow(() -> new RuntimeException("Registro de ponto não encontrado."));
        registro.setStatus(StatusPonto.VALIDADO);
        return paraDto(registroPontoRepository.save(registro));
    }

    public List<RegistroPontoResponseDto> listarPorFuncionario(Integer idFuncionario) {
        List<RegistroPonto> registros = registroPontoRepository.findByFuncionarioIdFuncionarioOrderByDataDesc(idFuncionario);
        return registros.stream()
                .map(this::paraDto)
                .collect(Collectors.toList());
    }

    private RegistroPontoResponseDto paraDto(RegistroPonto registro) {
        RegistroPontoResponseDto dto = new RegistroPontoResponseDto();
        dto.setIdRegistro(registro.getIdRegistro());
        dto.setData(registro.getData());
        dto.setHoraEntrada(registro.getHoraEntrada());
        dto.setHoraSaida(registro.getHoraSaida());
        dto.setStatus(registro.getStatus());
        dto.setJustificativa(registro.getJustificativa());
        if (registro.getFuncionario() != null) {
            dto.setIdFuncionario(registro.getFuncionario().getIdFuncionario());
        }
        return dto;
    }
}