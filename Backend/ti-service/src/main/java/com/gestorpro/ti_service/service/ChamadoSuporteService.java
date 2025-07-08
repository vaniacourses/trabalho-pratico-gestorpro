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
import java.util.stream.Collectors;

/**
 * Camada de Serviço para orquestrar toda a lógica de negócio
 * relacionada aos Chamados de Suporte.
 */
@Service
public class ChamadoSuporteService {

    @Autowired
    private IChamadoRepository chamadoRepository;

    // @Autowired
    // private IServiceNotificacao notificacaoService;

    /**
     * Abre um novo chamado de suporte. (RF-26, UC-03)
     * O solicitante é o próprio autor da requisição.
     * @param dto Os dados do formulário para abrir o chamado.
     * @param emailSolicitante O e-mail do usuário autenticado que está abrindo o chamado.
     * @return Os dados do chamado recém-criado.
     */
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

    /**
     * Inicia o atendimento de um chamado. Ação exclusiva da TI.
     * @param id O ID do chamado.
     */
    @Transactional
    public void iniciarAtendimento(Long id) {
        ChamadoSuporte chamado = getChamadoById(id);
        if (!chamado.podeIniciar()) {
            throw new BusinessException("O chamado #" + id + " não pode ser iniciado no estado atual: " + chamado.getStatus());
        }
        chamado.iniciarAtendimento();
        chamadoRepository.save(chamado);
    }

    /**
     * Resolve um chamado. Ação exclusiva da TI.
     * @param id O ID do chamado.
     * @param solucao A descrição da solução aplicada.
     */
    @Transactional
    public void resolverChamado(Long id, String solucao) {
        ChamadoSuporte chamado = getChamadoById(id);
        if (!chamado.podeResolver()) {
            throw new BusinessException("O chamado #" + id + " não pode ser resolvido no estado atual: " + chamado.getStatus());
        }
        chamado.resolver(solucao);
        chamadoRepository.save(chamado);
    }

    /**
     * Cancela um chamado. (RF-28 refinado)
     * Permitido para a equipe de TI (em qualquer chamado) ou para o próprio solicitante.
     * @param id O ID do chamado.
     * @param emailAtor O e-mail do usuário executando a ação.
     * @param rolesAtor As roles do usuário.
     */
    @Transactional
    public void cancelarChamado(Long id, String emailAtor, Collection<? extends GrantedAuthority> rolesAtor) {
        ChamadoSuporte chamado = getChamadoById(id);

        boolean isTI = rolesAtor.contains(new SimpleGrantedAuthority("ROLE_TI"));
        boolean isOwner = chamado.getSolicitanteEmail().equalsIgnoreCase(emailAtor);

        if (!isTI && !isOwner) {
            throw new ForbiddenException("Acesso negado. Você só pode cancelar seus próprios chamados.");
        }

        if (!chamado.podeCancelar()) {
            throw new BusinessException("O chamado #" + id + " não pode ser cancelado no estado atual: " + chamado.getStatus());
        }

        chamado.cancelar("Cancelado por " + emailAtor);
        chamadoRepository.save(chamado);
    }

    /**
     * Reabre um chamado que foi resolvido ou cancelado. Ação exclusiva da TI.
     * @param id O ID do chamado.
     * @param motivo O motivo da reabertura.
     */
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

    /**
     * Fecha um chamado para arquivamento. (RF-27)
     * Ação final e exclusiva da equipe de TI.
     * @param id O ID do chamado.
     * @param emailAtor O e-mail do membro da TI que está fechando.
     */
    @Transactional
    public void fecharChamado(Long id, String emailAtor) {
        ChamadoSuporte chamado = getChamadoById(id);
        if (!chamado.podeFechar()) {
            throw new BusinessException("O chamado #" + id + " não pode ser fechado no estado atual: " + chamado.getStatus());
        }
        chamado.fechar(emailAtor);
        chamadoRepository.save(chamado);
        // notificacaoService.notificarUsuario(chamado.getSolicitanteEmail(), "Seu chamado #" + id + " foi finalizado e fechado pela equipe de TI.");
    }

    // --- Métodos de Consulta ---

    public ChamadoResponse buscarChamadoPorId(Long id) {
        return mapToResponse(getChamadoById(id));
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
