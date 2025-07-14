package com.gestorpro.ti_service.service;

import com.gestorpro.ti_service.dto.AbrirChamadoRequest;
import com.gestorpro.ti_service.dto.ChamadoResponse;
import com.gestorpro.ti_service.exception.BusinessException;
import com.gestorpro.ti_service.exception.ForbiddenException;
import com.gestorpro.ti_service.exception.ResourceNotFoundException;
import com.gestorpro.ti_service.model.ChamadoSuporte;
import com.gestorpro.ti_service.model.Status;
import com.gestorpro.ti_service.repository.IChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ChamadoSuporteService {

    @Autowired
    private IChamadoRepository chamadoRepository;

    // @Autowired
    // private IServiceNotificacao notificacaoService;

    @Transactional
    public ChamadoResponse abrirChamado(AbrirChamadoRequest dto, String emailSolicitante) {
        ChamadoSuporte novoChamado = new ChamadoSuporte();
        novoChamado.setTipoProblema(dto.tipoProblema());
        novoChamado.setUrgencia(dto.urgencia());
        novoChamado.setSolicitanteEmail(emailSolicitante); // Identificador do dono do chamado

        ChamadoSuporte chamadoSalvo = chamadoRepository.save(novoChamado);
        // notificacaoService.notificarEquipeTI("Novo chamado aberto #" + chamadoSalvo.getId() + " por " + emailSolicitante);

        return mapToResponse(chamadoSalvo);
    }

    @Transactional
    public void iniciarAtendimento(Long id) {
        ChamadoSuporte chamado = getChamadoById(id);
        if (!chamado.podeIniciar()) {
            throw new BusinessException("O chamado #" + id + " não pode ser iniciado no estado atual: " + chamado.getStatus());
        }
        chamado.iniciarAtendimento();
        chamadoRepository.save(chamado);
    }

    @Transactional
    public void resolverChamado(Long id, String solucao) {
        ChamadoSuporte chamado = getChamadoById(id);
        if (!chamado.podeResolver()) {
            throw new BusinessException("O chamado #" + id + " não pode ser resolvido no estado atual: " + chamado.getStatus());
        }
        chamado.resolver(solucao);
        chamadoRepository.save(chamado);
    }

    @Transactional
    public void cancelarChamado(Long id, String motivo, String emailAtor, Collection<? extends GrantedAuthority> rolesAtor) {
        ChamadoSuporte chamado = getChamadoById(id);

        if (!chamado.podeCancelar() || !Objects.equals(emailAtor, chamado.getSolicitanteEmail())) {
            throw new BusinessException("O chamado #" + id + " não pode ser cancelado no seu estado atual: " + chamado.getStatus());
        }
        chamado.cancelar(motivo);
        chamadoRepository.save(chamado);
    }


    @Transactional
    public void reabrirChamado(Long id, String motivo) {
        ChamadoSuporte chamado = getChamadoById(id);
        if(!chamado.podeReabrir()) {
            throw new BusinessException("O chamado #" + id + " não pode ser reaberto no estado atual: " + chamado.getStatus());
        }
        chamado.reabrir(motivo);
        chamadoRepository.save(chamado);
        // notificacaoService.notificarEquipeTI("Chamado #" + id + " foi reaberto. Motivo: " + motivo);
    }

    @Transactional
    public void fecharChamado(Long id) {
        ChamadoSuporte chamado = getChamadoById(id);
        if (!chamado.podeFechar()) {
            throw new BusinessException("O chamado #" + id + " não pode ser fechado no estado atual: " + chamado.getStatus());
        }
        chamado.fechar();
        chamadoRepository.save(chamado);
        // notificacaoService.notificarUsuario(chamado.getSolicitanteEmail(), "Seu chamado #" + id + " foi finalizado e fechado pela equipe de TI.");
    }

    // --- Métodos de Consulta ---

    public ChamadoResponse buscarChamadoPorId(Long id) {
        return mapToResponse(getChamadoById(id));
    }

    public ChamadoResponse buscarChamadoPorId(Long id, Boolean isTI, String email) {
        ChamadoSuporte chamado = getChamadoById(id);
        if(chamado.getSolicitanteEmail().equals(email) || isTI){
            return mapToResponse(getChamadoById(id));
        }
        else {
            throw new ForbiddenException("O chamado #" + id + " não pode ser exibido para você.");
        }
    }

    public List<ChamadoResponse> listarTodosChamados() {
        return chamadoRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ChamadoResponse> listarChamadosPorSolicitante(String emailSolicitante) {
        return chamadoRepository.findBySolicitanteEmail(emailSolicitante).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    private ChamadoSuporte getChamadoById(Long id) {
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado com ID " + id + " não encontrado."));
    }

    private ChamadoResponse mapToResponse(ChamadoSuporte chamado) {
        return new ChamadoResponse(
                chamado.getId(),
                chamado.getTipoProblema(),
                chamado.getUrgencia(),
                chamado.getSolicitanteEmail(),
                chamado.getStatus(),
                chamado.getDataHoraCriacao()
        );
    }

    public List<ChamadoResponse> listarChamadosPorStatus(Status status) {
        List<ChamadoSuporte> chamados = chamadoRepository.findByStatus(status);

        return chamados.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
