import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';
import * as chamadoService from '../services/api_ti';
import type { Chamado } from '../types/chamado.types';
import { useAuth } from '../hooks/useAuth';

const TicketDetailPage: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();
    const { isAuthenticated, isLoading: isAuthLoading } = useAuth();

    const [chamado, setChamado] = useState<Chamado | null>(null);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchChamadoDetails = async () => {
            if (!id) {
                setError("ID do chamado não fornecido.");
                setIsLoading(false);
                return;
            }
            if (isAuthLoading) return;

            if (!isAuthenticated) {
                navigate('/login');
                return;
            }

            try {
                setIsLoading(true);
                setError(null);
                const response = await chamadoService.buscarChamadoPorId(parseInt(id)); 
                setChamado(response.data);
            } catch (err) {
                console.error("Erro ao buscar detalhes do chamado:", err);
                setError("Não foi possível carregar os detalhes do chamado. Por favor, tente novamente.");
            } finally {
                setIsLoading(false);
            }
        };

        fetchChamadoDetails();
    }, [id, navigate, isAuthenticated, isAuthLoading]);

    if (isAuthLoading || isLoading) {
        return (
            <>
                <Navbar />
                <div className="text-center p-5">
                    <div className="spinner-border text-primary" role="status">
                    </div>
                    <p className="mt-2">Carregando detalhes do chamado...</p>
                </div>
            </>
        );
    }

    if (error) {
        return (
            <>
                <Navbar />
                <div className="container mt-4">
                    <div className="alert alert-danger">{error}</div>
                    <button className="btn btn-secondary" onClick={() => navigate(-1)}>Voltar</button>
                </div>
            </>
        );
    }

    if (!chamado) {
        return (
            <>
                <Navbar />
                <div className="container mt-4">
                    <div className="alert alert-info">Nenhum chamado encontrado com o ID fornecido.</div>
                    <button className="btn btn-secondary" onClick={() => navigate(-1)}>Voltar</button>
                </div>
            </>
        );
    }

    return (
        <>
            <Navbar />
            <main className="container mt-4 mb-5">
                <button className="btn btn-secondary mb-3" onClick={() => navigate(-1)}>
                    <i className="bi bi-arrow-left me-2"></i>Voltar
                </button>
                <h2 className="mb-4">Detalhes do Chamado #{chamado.id}</h2>
                <div className="card shadow-sm mb-4">
                    <div className="card-body">
                        <h4 className="card-title">Tipo de Problema: {chamado.tipoProblema}</h4>
                        <p className="card-subtitle text-muted mb-2">
                            Solicitante: <strong>{chamado.solicitanteEmail}</strong>
                        </p>
                        <p className="card-subtitle text-muted mb-2">
                            Data de Criação: {new Date(chamado.dataHoraCriacao).toLocaleString()}
                        </p>
                        <hr />
                        <h5 className="mt-3">Urgência</h5>
                        <p className="card-text">{chamado.urgencia}</p>
                        
                        <h5 className="mt-3">Status Atual</h5>
                        <p className={`badge ${chamado.status === 'ABERTO' ? 'bg-primary' : 
                                            chamado.status === 'EM_ANDAMENTO' ? 'bg-warning text-dark' :
                                            chamado.status === 'RESOLVIDO' ? 'bg-success' :
                                            'bg-secondary'}`}>
                            {chamado.status.replace(/_/g, ' ')}
                        </p>
                    </div>
                </div>
            </main>
        </>
    );
};

export default TicketDetailPage;