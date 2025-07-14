import React, { useState, useEffect, useCallback } from 'react';
import Navbar from '../components/Navbar';
import NewTicketForm from '../components/TINewTicketForm';
import TicketList from '../components/TITicketList';
import SimpleConfirmationModal from '../components/TISimpleModal';
import TicketFilters from '../components/TITicketFilters';
import { useAuth } from '../hooks/useAuth';
import "../components/global.css"

import type { AbrirChamadoRequest, Chamado, Status } from '../types/chamado.types';
import type { TicketAction } from '../components/TITicketRow';

import * as chamadoService from '../services/api_ti';

const ITPage: React.FC = () => {
    const { user, isAuthenticated, isLoading } = useAuth();
    const isTI = user?.roles.includes('ROLE_TI') ?? false;

    const [chamados, setChamados] = useState<Chamado[]>([]);
    const [isTicketsLoading, setIsTicketsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const [isFormVisible, setIsFormVisible] = useState(false);
    const [isSubmitting, setIsSubmitting] = useState(false);

    const [activeFilterStatus, setActiveFilterStatus] = useState<Status | 'Todos os Status'>('Todos os Status');

    const [activeModal, setActiveModal] = useState<TicketAction | null>(null);
    const [selectedTicketId, setSelectedTicketId] = useState<number | null>(null);

    const fetchChamados = useCallback(async () => {
        setIsTicketsLoading(true);
        setError(null);
        try {
            let response;
            if (isTI) {
                if (activeFilterStatus === 'Todos os Status') {
                    response = await chamadoService.listarTodosChamados();
                } else {
                    response = await chamadoService.listarChamadosPorStatus(activeFilterStatus);
                }
            } else {
                response = await chamadoService.listarMeusChamados();
            }
            setChamados(response.data);
        } catch (err) {
            console.error("Erro ao carregar chamados:", err);
            setError("Não foi possível carregar os chamados. Por favor, tente novamente.");
        } finally {
            setIsTicketsLoading(false);
        }
    }, [isTI, activeFilterStatus]);

    useEffect(() => {
        if (!isLoading && isAuthenticated) {
            fetchChamados();
        }
    }, [fetchChamados, isLoading, isAuthenticated]);

    const handleFilterChange = useCallback((status: Status | 'Todos os Status') => {
        setActiveFilterStatus(status);
    }, []);

    const handleActionClick = useCallback((action: TicketAction, ticketId: number) => {
        setSelectedTicketId(ticketId);
        setActiveModal(action);
    }, []);

    const handleNewTicketSubmit = async (ticketData: AbrirChamadoRequest) => {
        setIsSubmitting(true);
        try {
            await chamadoService.abrirChamado(ticketData);
            setIsFormVisible(false);
            await fetchChamados();
        } catch (err) {
            console.error("Erro ao abrir o chamado:", err);
            } finally {
            setIsSubmitting(false);
        }
    };

    const handleModalSubmit = async (text: string) => {
        if (!activeModal || selectedTicketId === null) return;

        setIsSubmitting(true);
        let actionDescription = '';

        try {
            switch (activeModal) {
                case 'cancelar':
                    actionDescription = 'cancelar o chamado';
                    await chamadoService.cancelarChamado(selectedTicketId, { motivo: text });
                    break;
                case 'resolver':
                    actionDescription = 'resolver o chamado';
                    await chamadoService.resolverChamado(selectedTicketId, { solucao: text });
                    break;
                case 'reabrir':
                    actionDescription = 'reabrir o chamado';
                    await chamadoService.reabrirChamado(selectedTicketId, { motivo: text });
                    break;
                case 'iniciar':
                    actionDescription = 'iniciar o atendimento do chamado';
                    await chamadoService.iniciarAtendimento(selectedTicketId);
                    break;
                case 'fechar':
                    actionDescription = 'fechar o chamado';
                    await chamadoService.fecharChamado(selectedTicketId);
                    break;
                default:
                    console.warn(`Ação de modal desconhecida: ${activeModal}`);
                    return;
            }
            
            await fetchChamados();
            handleCloseModal();
        } catch (err) {
            console.error(`Erro ao executar a ação '${activeModal}' no chamado ${selectedTicketId}:`, err);
        } finally {
            setIsSubmitting(false);
        }
    };

    const handleCloseModal = useCallback(() => {
        setActiveModal(null);
        setSelectedTicketId(null);
    }, []);

    const modalConfigs = {
        cancelar: { 
            title: 'Cancelar Chamado', 
            label: 'Motivo do Cancelamento', 
            placeholder: 'Descreva por que o chamado está sendo cancelado...', 
            submitButtonText: 'Confirmar Cancelamento',
            requiresInput: true 
        },
        resolver: { 
            title: 'Resolver Chamado', 
            label: 'Solução Aplicada', 
            placeholder: 'Descreva a solução para este problema...', 
            submitButtonText: 'Marcar como Resolvido',
            requiresInput: true 
        },
        reabrir: { 
            title: 'Reabrir Chamado', 
            label: 'Motivo da Reabertura', 
            placeholder: 'Descreva por que o chamado precisa ser reaberto...', 
            submitButtonText: 'Confirmar Reabertura',
            requiresInput: true 
        },
        iniciar: { 
            title: 'Iniciar Atendimento', 
            label: 'Confirmação', 
            message: 'Tem certeza que deseja iniciar o atendimento deste chamado?', 
            submitButtonText: 'Confirmar Início',
            requiresInput: false 
        },
        fechar: { 
            title: 'Fechar Chamado', 
            label: 'Confirmação',
            message: 'Tem certeza que deseja fechar este chamado?', 
            submitButtonText: 'Confirmar Fechamento',
            requiresInput: false 
        },
    };
    const currentModalConfig = activeModal ? modalConfigs[activeModal as keyof typeof modalConfigs] : null;

    if (isLoading) {
        return <div className="text-center p-5">Carregando informações do usuário...</div>;
    }

    if (!isAuthenticated) {
        return <div className="alert alert-warning text-center m-5">Você precisa estar logado para acessar esta página.</div>;
    }

    return (
        <>
            <Navbar />
            <main className="container mt-4 mb-5">
                <header className="mb-4">
                    <h1 className="fw-bolder">
                        {isTI ? 'Painel de Atendimento de TI' : 'Meus Chamados'}
                    </h1>
                    <p className="text-secondary fs-5">
                        {isTI ? 'Gerencie todos os chamados da empresa.' : 'Acompanhe seus chamados de suporte técnico.'}
                    </p>
                </header>

                <div className="d-grid gap-2 d-md-flex justify-content-md-end mb-4">
                    <button className="btn btn-primary fw-semibold" type="button" onClick={() => setIsFormVisible(prev => !prev)}>
                        <i className="bi bi-plus-circle me-1"></i>
                        {isFormVisible ? 'Fechar Formulário' : 'Abrir Novo Chamado'}
                    </button>
                </div>

                <div className={`collapse ${isFormVisible ? 'show' : ''}`}>
                    <NewTicketForm onTicketSubmit={handleNewTicketSubmit} isSubmitting={isSubmitting} />
                </div>

                {isTI && (
                    <TicketFilters onFilter={handleFilterChange} />
                )}

                {isTicketsLoading && (
                    <div className="text-center p-5">
                        <div className="spinner-border text-primary" role="status">
                            <span className="visually-hidden">Carregando...</span>
                        </div>
                    </div>
                )}
                {error && <div className="alert alert-danger">{error}</div>}
                
                {!isTicketsLoading && !error && (
                    <TicketList
                        tickets={chamados}
                        user={user}
                        onActionClick={handleActionClick}
                    />
                )}
                
                {currentModalConfig && (
                    <SimpleConfirmationModal
                        show={!!activeModal}
                        onCancel={handleCloseModal}
                        onConfirm={handleModalSubmit}
                        isProcessing={isSubmitting}
                        {...currentModalConfig}
                    />
                )}
            </main>
        </>
    );
};

export default ITPage;