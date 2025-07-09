import { useState, useEffect, useCallback } from 'react';
import Navbar from '../components/Navbar';
import NewTicketForm from '../components/TINewTicketForm'; 
import TicketList from '../components/TITicketList';
import ActionModal from '../components/TIActionModal';
import TicketFilters from '../components/TITicketFilters'; // Importa o filtro
import { useAuth } from '../contexts/AuthContext';
import * as chamadoService from '../services/api_ti';
import type { AbrirChamadoRequest, Chamado, Status } from '../types/chamado.types';
import type { TicketAction } from '../components/TITicketRow';

const ITPage: React.FC = () => {
    const { user } = useAuth();
    const isTI = user?.roles.includes('ROLE_TI') ?? false;

    const [chamados, setChamados] = useState<Chamado[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [isFormVisible, setIsFormVisible] = useState(false);
    const [isSubmitting, setIsSubmitting] = useState(false);
    
    // Estado para os filtros
    const [activeFilters, setActiveFilters] = useState<{ status: Status | 'TODOS' }>({ status: 'TODOS' });
    
    // Lógica dos modais (sem alterações)
    const [activeModal, setActiveModal] = useState<TicketAction | null>(null);
    const [selectedTicketId, setSelectedTicketId] = useState<number | null>(null);

    // Função de busca agora considera os filtros e a role do usuário
    const fetchChamados = useCallback(async () => {
        setIsLoading(true);
        setError(null);
        try {
            let response;
            if (isTI) {
                // Se for TI, verifica se há um filtro de status ativo
                if (activeFilters.status !== 'TODOS') {
                    response = await chamadoService.listarChamadosPorStatus(activeFilters.status);
                } else {
                    response = await chamadoService.listarTodosChamados();
                }
            } else {
                // Se não for TI, busca apenas os chamados do próprio usuário
                response = await chamadoService.listarMeusChamados();
            }
            setChamados(response.data);
        } catch (err) {
            setError("Não foi possível carregar os chamados.");
        } finally {
            setIsLoading(false);
        }
    }, [isTI, activeFilters]); // Roda a busca quando a role ou os filtros mudam

    useEffect(() => {
        fetchChamados();
    }, [fetchChamados]);

    // Função para ser chamada pelo componente de filtro
    const handleFilterChange = (filters: { status: Status | 'TODOS' }) => {
        setActiveFilters(filters);
    };

    const handleActionClick = (action: TicketAction, ticketId: number) => {
        setSelectedTicketId(ticketId);
        if (action === 'iniciar' || action === 'fechar') {
            handleDirectAction(action, ticketId);
        } else {
            setActiveModal(action);
        }
    };

    // Função para criar um novo chamado
    const handleNewTicketSubmit = async (ticketData: AbrirChamadoRequest) => {
        setIsSubmitting(true);
        try {
            await chamadoService.abrirChamado(ticketData);
            setIsFormVisible(false);
            await fetchChamados();
        } catch (err) {
            alert("Ocorreu um erro ao tentar abrir o chamado.");
        } finally {
            setIsSubmitting(false);
        }
    };
    
    // --- CÓDIGO COMPLETADO ABAIXO ---

    /**
     * Lida com ações que não precisam de input em um modal, como Iniciar e Fechar.
     */
    const handleDirectAction = async (action: 'iniciar' | 'fechar', ticketId: number) => {
        const actionVerb = action === 'iniciar' ? 'iniciar o atendimento' : 'fechar';
        if (!window.confirm(`Tem certeza que deseja ${actionVerb} este chamado?`)) return;

        setIsSubmitting(true);
        try {
            if (action === 'iniciar') {
                await chamadoService.iniciarAtendimento(ticketId);
            } else if (action === 'fechar') {
                // A API espera o email do ator para fechar o chamado
                await chamadoService.fecharChamado(ticketId);
            }
            await fetchChamados();
        } catch (err) {
            console.error(`Erro ao ${action} o chamado:`, err);
            alert(`Ocorreu um erro ao tentar ${actionVerb} o chamado.`);
        } finally {
            setIsSubmitting(false);
        }
    };

    /**
     * Lida com a submissão do formulário do modal (Resolver, Cancelar, Reabrir).
     */
    const handleModalSubmit = async (text: string) => {
        if (!activeModal || !selectedTicketId) return;

        setIsSubmitting(true);
        try {
            switch (activeModal) {
                case 'cancelar':
                    await chamadoService.cancelarChamado(selectedTicketId, { motivo: text });
                    break;
                case 'resolver':
                    await chamadoService.resolverChamado(selectedTicketId, { solucao: text });
                    break;
                case 'reabrir':
                    await chamadoService.reabrirChamado(selectedTicketId, { motivo: text });
                    break;
            }
            await fetchChamados();
            handleCloseModal();
        } catch (err) {
            console.error(`Erro ao executar a ação '${activeModal}':`, err);
            alert(`Ocorreu um erro ao tentar ${activeModal} o chamado.`);
        } finally {
            setIsSubmitting(false);
        }
    };
    
    const handleCloseModal = () => {
        setActiveModal(null);
        setSelectedTicketId(null);
    };

    // Objeto de configuração para popular o modal genérico
    const modalConfigs = {
        cancelar: { title: 'Cancelar Chamado', label: 'Motivo do Cancelamento', placeholder: 'Descreva por que o chamado está sendo cancelado...', submitButtonText: 'Confirmar Cancelamento' },
        resolver: { title: 'Resolver Chamado', label: 'Solução Aplicada', placeholder: 'Descreva a solução para este problema...', submitButtonText: 'Marcar como Resolvido' },
        reabrir: { title: 'Reabrir Chamado', label: 'Motivo da Reabertura', placeholder: 'Descreva por que o chamado precisa ser reaberto...', submitButtonText: 'Confirmar Reabertura' },
    };
    const currentModalConfig = activeModal && modalConfigs[activeModal as keyof typeof modalConfigs];


    return (
        <>
            <Navbar/>
            <main className="container mt-4 mb-5">
                <header className="mb-4">
                    <h1 className="fw-bolder">Fila de Atendimento - TI</h1>
                    <p className="text-secondary fs-5">Abra e acompanhe seus chamados de suporte técnico.</p>
                </header>

                <div className="d-grid gap-2 d-md-flex justify-content-md mb-4">
                    <button className="btn btn-primary fw-semibold" type="button" onClick={() => setIsFormVisible(prev => !prev)}>
                        <i className="bi bi-plus-circle me-1"></i>
                        {isFormVisible ? 'Fechar Formulário' : 'Abrir Novo Chamado'}
                    </button>
                </div>

                <div className={`collapse ${isFormVisible ? 'show' : ''}`}>
                    <NewTicketForm onTicketSubmit={handleNewTicketSubmit} isSubmitting={isSubmitting} />
                </div>
                
                {/* Mostra os filtros apenas se o usuário for da TI */}
                {isTI && <TicketFilters onFilterChange={handleFilterChange} isFiltering={isLoading} />}
                
                {isLoading && <div className="text-center p-5"><div className="spinner-border" role="status"><span className="visually-hidden">Carregando...</span></div></div>}
                {error && <div className="alert alert-danger">{error}</div>}
                
                {!isLoading && !error && (
                    <TicketList 
                        tickets={chamados} 
                        user={user}
                        onActionClick={handleActionClick}
                    />
                )}
                
                {/* Renderiza o modal genérico com as props corretas, somente quando necessário */}
                {currentModalConfig && (
                    <ActionModal
                        show={!!activeModal}
                        onClose={handleCloseModal}
                        onSubmit={handleModalSubmit}
                        isSubmitting={isSubmitting}
                        {...currentModalConfig}
                    />
                )}
            </main>
        </>
    );
};

export default ITPage;