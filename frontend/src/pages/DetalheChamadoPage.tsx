import { useState, useEffect, useCallback } from 'react';
import { useParams, Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import * as chamadoService from '../services/api_ti';
import type { Chamado } from '../types/chamado.types';
import type { TicketAction } from '../components/TITicketRow';
import Navbar from '../components/Navbar';
import ActionModal from '../components/TIActionModal';
import ChamadoActions from '../components/TIChamadoActions';
import { StatusBadge } from '../components/TITicketRow';

const DetalheChamadoPage: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const { user } = useAuth();
    const isTI = user?.roles.includes('ROLE_TI') ?? false;
    
    const [chamado, setChamado] = useState<Chamado | null>(null);
    const [isLoading, setIsLoading] = useState(true);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [error, setError] = useState<string | null>(null);
    
    const [activeModal, setActiveModal] = useState<TicketAction | null>(null);

    const fetchDetalhesChamado = useCallback(async () => {
        if (!id || isNaN(parseInt(id, 10))) {
            setError("ID do chamado inválido.");
            setIsLoading(false);
            return;
        }
        
        setIsLoading(true);
        try {
            const response = await chamadoService.buscarChamadoPorId(parseInt(id, 10));
            setChamado(response.data);
        } catch (err) {
            setError("Chamado não encontrado ou você não tem permissão para vê-lo.");
        } finally {
            setIsLoading(false);
        }
    }, [id]);

    useEffect(() => {
        fetchDetalhesChamado();
    }, [fetchDetalhesChamado]);

    const handleActionClick = (action: TicketAction) => {
        if (!chamado) return;
        
        if (action === 'iniciar' || action === 'fechar') {
            handleDirectAction(action, chamado.id);
        } else {
            setActiveModal(action);
        }
    };

    const handleDirectAction = async (action: 'iniciar' | 'fechar', ticketId: number) => {
        if (!window.confirm(`Tem certeza que deseja ${action} este chamado?`)) return;
        
        setIsSubmitting(true);
        try {
            if (action === 'iniciar') await chamadoService.iniciarAtendimento(ticketId);
            if (action === 'fechar') await chamadoService.fecharChamado(ticketId);
            await fetchDetalhesChamado();
        } catch (err) {
            alert(`Erro ao ${action} o chamado.`);
        } finally {
            setIsSubmitting(false);
        }
    };

    const handleModalSubmit = async (text: string) => {
        if (!activeModal || !chamado) return;

        setIsSubmitting(true);
        try {
            switch (activeModal) {
                case 'cancelar':
                    await chamadoService.cancelarChamado(chamado.id, { motivo: text });
                    break;
                case 'resolver':
                    await chamadoService.resolverChamado(chamado.id, { solucao: text });
                    break;
                case 'reabrir':
                    await chamadoService.reabrirChamado(chamado.id, { motivo: text });
                    break;
            }
            await fetchDetalhesChamado();
            handleCloseModal();
        } catch (err) {
            alert(`Erro ao executar a ação: ${activeModal}.`);
        } finally {
            setIsSubmitting(false);
        }
    };
    
    const handleCloseModal = () => setActiveModal(null);

    const modalConfigs = {
        cancelar: { title: 'Cancelar Chamado', label: 'Motivo do Cancelamento', placeholder: 'Descreva por que o chamado está sendo cancelado...', submitButtonText: 'Confirmar Cancelamento' },
        resolver: { title: 'Resolver Chamado', label: 'Solução Aplicada', placeholder: 'Descreva a solução para este problema...', submitButtonText: 'Marcar como Resolvido' },
        reabrir: { title: 'Reabrir Chamado', label: 'Motivo da Reabertura', placeholder: 'Descreva por que o chamado precisa ser reaberto...', submitButtonText: 'Confirmar Reabertura' },
    };
    const currentModalConfig = activeModal && modalConfigs[activeModal as keyof typeof modalConfigs];

    if (isLoading) {
        return <div className="text-center p-5"><div className="spinner-border"></div></div>;
    }

    if (error || !chamado) {
        return (
            <>
                <Navbar />
                <main className="container mt-4"><div className="alert alert-danger">{error || "Chamado não encontrado."}</div></main>
            </>
        );
    }

    return (
        <>
            <Navbar />
            <main className="container mt-4 mb-5">
                <div className="d-flex justify-content-between align-items-center mb-4">
                    <h1 className="fw-bolder mb-0">Detalhes do Chamado #{chamado.id}</h1>
                    <Link to="/ti" className="btn btn-secondary">&larr; Voltar para a Lista</Link>
                </div>

                <div className="card">
                    <div className="card-body p-4">
                        <div className="row mb-3">
                            <div className="col-md-8">
                                <h6 className="text-muted">Problema</h6>
                                <p className="fs-5">{chamado.tipoProblema}</p>
                            </div>
                            <div className="col-md-2">
                                <h6 className="text-muted">Urgência</h6>
                                <p className="fs-5">{chamado.urgencia}</p>
                            </div>
                            <div className="col-md-2">
                                <h6 className="text-muted">Status</h6>
                                <p className="fs-5"><StatusBadge status={chamado.status} /></p>
                            </div>
                        </div>
                        <hr />
                        <div className="row">
                             <div className="col-md-6">
                                <h6 className="text-muted">Solicitante</h6>
                                <p>{chamado.solicitanteEmail}</p>
                            </div>
                             <div className="col-md-6">
                                <h6 className="text-muted">Data de Abertura</h6>
                                <p>{new Date(chamado.dataHoraCriacao).toLocaleString('pt-BR')}</p>
                            </div>
                        </div>

                        {/* Renderiza o painel de ações apenas para a equipe de TI */}
                        {isTI && <ChamadoActions chamado={chamado} onActionClick={handleActionClick} />}
                    </div>
                </div>
            </main>

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
        </>
    );
};

export default DetalheChamadoPage;