package com.gestorpro.ti_service.model;

import com.gestorpro.ti_service.model.state.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * A entidade ChamadoSuporte representa um ticket de suporte no sistema.
 * Ela gerencia seu próprio ciclo de vida através do Padrão de Projeto State.
 */
@Entity
@Table(name = "chamados_suporte")
public class ChamadoSuporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Campos de Dados do Chamado ---
    @Column(nullable = false)
    private String tipoProblema;

    @Column(nullable = false)
    private String urgencia;

    @Column(nullable = false)
    private String solicitanteEmail;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataHoraCriacao;

    @Column(columnDefinition = "TEXT")
    private String solucao;

    @Column(columnDefinition = "TEXT")
    private String motivoCancelamento;

    @Column(columnDefinition = "TEXT")
    private String motivoReabertura;

    private String fechadoPor;
    private LocalDateTime dataFechamento;

    // --- Campos de Gerenciamento de Estado ---

    /**
     * O estado persistido no banco de dados.
     * Este é um valor simples (String) que representa o estado atual.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    /**
     * O objeto de estado atual, que contém a lógica de comportamento.
     * É @Transient para que o JPA o ignore e não tente salvá-lo no banco.
     */
    @Transient
    private EstadoChamado estadoAtual;

    // --- Construtor e Ciclo de Vida JPA ---

    /**
     * Construtor padrão que define o estado inicial de qualquer novo chamado.
     */
    public ChamadoSuporte() {
        this.status = Status.ABERTO;
        this.estadoAtual = new EstadoAberto(); // Todo novo chamado começa como "Aberto"
        this.dataHoraCriacao = LocalDateTime.now();
    }

    /**
     * Método de callback do JPA. É executado logo após a entidade ser carregada do banco.
     * Usamos para "hidratar" o objeto de estado correto com base no enum persistido.
     */
    @PostLoad
    private void carregarEstado() {
        if (this.status != null) {
            // Usando o padrão "Enum como Fábrica" para obter a instância do estado
            this.estadoAtual = this.status.obterInstancia();
        }
    }

    // --- Métodos de Ação (Delegação para o Estado) ---
    // O serviço deve sempre verificar com o método "pode..." antes de chamar estes.

    public void iniciarAtendimento() {
        ((PodeIniciar) this.estadoAtual).iniciarAtendimento(this);
    }

    public void resolver(String solucao) {
        ((PodeResolver) this.estadoAtual).resolver(this, solucao);
    }

    public void cancelar(String motivo) {
        ((PodeCancelar) this.estadoAtual).cancelar(this, motivo);
    }

    public void fechar(String ator) {
        ((PodeFechar) this.estadoAtual).fechar(this, ator);
    }

    public void reabrir(String motivo) {
        ((PodeReabrir) this.estadoAtual).reabrir(this, motivo);
    }

    // --- Métodos de Verificação de Capacidade ---
    // Permitem que a camada de serviço consulte as ações disponíveis com segurança.

    public boolean podeIniciar() { return this.estadoAtual instanceof PodeIniciar; }
    public boolean podeResolver() { return this.estadoAtual instanceof PodeResolver; }
    public boolean podeCancelar() { return this.estadoAtual instanceof PodeCancelar; }
    public boolean podeFechar() { return this.estadoAtual instanceof PodeFechar; }
    public boolean podeReabrir() { return this.estadoAtual instanceof PodeReabrir; }


    // --- Getters e Setters ---
    // (Gerados pelo Lombok ou manualmente)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipoProblema() { return tipoProblema; }
    public void setTipoProblema(String tipoProblema) { this.tipoProblema = tipoProblema; }
    public String getUrgencia() { return urgencia; }
    public void setUrgencia(String urgencia) { this.urgencia = urgencia; }
    public String getSolicitanteEmail() { return solicitanteEmail; }
    public void setSolicitanteEmail(String solicitanteEmail) { this.solicitanteEmail = solicitanteEmail; }
    public LocalDateTime getDataHoraCriacao() { return dataHoraCriacao; }
    public void setDataHoraCriacao(LocalDateTime dataHoraCriacao) { this.dataHoraCriacao = dataHoraCriacao; }
    public String getSolucao() { return solucao; }
    public void setSolucao(String solucao) { this.solucao = solucao; }
    public String getMotivoCancelamento() { return motivoCancelamento; }
    public void setMotivoCancelamento(String motivoCancelamento) { this.motivoCancelamento = motivoCancelamento; }
    public String getMotivoReabertura() { return motivoReabertura; }
    public void setMotivoReabertura(String motivoReabertura) { this.motivoReabertura = motivoReabertura; }
    public String getFechadoPor() { return fechadoPor; }
    public void setFechadoPor(String fechadoPor) { this.fechadoPor = fechadoPor; }
    public LocalDateTime getDataFechamento() { return dataFechamento; }
    public void setDataFechamento(LocalDateTime dataFechamento) { this.dataFechamento = dataFechamento; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public void setEstadoAtual(EstadoChamado estadoAtual) { this.estadoAtual = estadoAtual; }
}