package com.gestorpro.ti_service.model;

import com.gestorpro.ti_service.model.state.EstadoAberto;
import com.gestorpro.ti_service.model.state.EstadoChamado;
import com.gestorpro.ti_service.model.state.PodeCancelar;
import com.gestorpro.ti_service.model.state.PodeFechar;
import com.gestorpro.ti_service.model.state.PodeIniciar;
import com.gestorpro.ti_service.model.state.PodeReabrir;
import com.gestorpro.ti_service.model.state.PodeResolver;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

//Gera todos os get e set
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "chamados_suporte")
public class ChamadoSuporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipoProblema;

    @Column(nullable = false)
    private String urgencia;

    @Column(name = "solicitante_email", nullable = false)
    private String solicitanteEmail;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataHoraCriacao;

    @Column(columnDefinition = "TEXT")
    private String solucao;

    @Column(columnDefinition = "TEXT")
    private String motivoCancelamento;

    @Column(columnDefinition = "TEXT")
    private String motivoReabertura;

    private LocalDateTime dataFechamento;

    // Campo persistente que define o estado
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    //Objeto de estado usado durante a execucao e ignorado pelo JPA.
    @Transient
    private EstadoChamado estadoAtual;

    @PrePersist // Método executado antes de salvar pela primeira vez
    private void preencherDadosIniciais() {
        this.dataHoraCriacao = LocalDateTime.now();
        this.status = Status.ABERTO;
        this.estadoAtual = new EstadoAberto();
    }

    @PostLoad // Método executado após carregar
    private void carregarEstado() {
        if (this.status != null) {
            this.estadoAtual = this.status.obterInstancia();
        }
    }

    public void iniciarAtendimento() { ((PodeIniciar) this.estadoAtual).iniciarAtendimento(this); }
    public void resolver(String solucao) { ((PodeResolver) this.estadoAtual).resolver(this, solucao); }
    public void cancelar(String motivo) { ((PodeCancelar) this.estadoAtual).cancelar(this, motivo); }
    public void fechar() { ((PodeFechar) this.estadoAtual).fechar(this); }
    public void reabrir(String motivo) { ((PodeReabrir) this.estadoAtual).reabrir(this, motivo); }

    public boolean podeIniciar() { return this.estadoAtual instanceof PodeIniciar; }
    public boolean podeResolver() { return this.estadoAtual instanceof PodeResolver; }
    public boolean podeCancelar() { return this.estadoAtual instanceof PodeCancelar; }
    public boolean podeFechar() { return this.estadoAtual instanceof PodeFechar; }
    public boolean podeReabrir() { return this.estadoAtual instanceof PodeReabrir; }
}