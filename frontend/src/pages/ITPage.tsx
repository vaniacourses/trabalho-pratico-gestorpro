import { useState, useEffect, useCallback } from 'react';
import Navbar from '../components/Navbar';
import NewTicketForm, { type FormDataState } from '../components/NewTicketForm'; 
import TicketList from '../components/TicketList';
import api from '../services/api';

// Definimos um tipo para os dados de um chamado, espelhando o DTO de resposta do backend
export interface Chamado {
    id: number;
    tipoProblema: string;
    urgencia: string;
    solicitanteEmail: string;
    status: string;
    dataHoraCriacao: string;
}

const ITPage: React.FC = () => {
    // Lógica para visibilidade do formulário (mantida do código original)
    const [isFormVisible, setIsFormVisible] = useState(false);
    const toggleFormVisibility = () => setIsFormVisible(prev => !prev);

    // Estados para gerenciar os dados e a UI
    const [chamados, setChamados] = useState<Chamado[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [isSubmitting, setIsSubmitting] = useState(false);

    // Função para buscar os chamados da API.
    const fetchChamados = useCallback(async () => {
        setIsLoading(true);
        setError(null);
        try {
            // Endpoint para o usuário ver seus próprios chamados
            const response = await api.get('/chamados-suporte/meus-chamados');
            setChamados(response.data);
        } catch (err) {
            console.error("Falha ao buscar chamados:", err);
            setError("Não foi possível carregar os chamados. Tente novamente mais tarde.");
        } finally {
            setIsLoading(false);
        }
    }, []);

    // Hook que executa a busca de dados quando a página carrega
    useEffect(() => {
        fetchChamados();
    }, [fetchChamados]);

    // Função para lidar com o envio do formulário de novo chamado
    const handleNewTicketSubmit = async (ticketData: FormDataState) => {
        setIsSubmitting(true);
        try {
            // Endpoint para criar um novo chamado
            await api.post('/chamados-suporte', ticketData);
            
            setIsFormVisible(false); // Esconde o formulário
            await fetchChamados(); // Atualiza a lista na tela
        } catch (err) {
            console.error("Erro ao criar chamado:", err);
            alert("Ocorreu um erro ao tentar abrir o chamado.");
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <>
            <Navbar/>
            {/* A estrutura visual (JSX) abaixo foi mantida idêntica à original */}
            <main className="container mt-4 mb-5">
                <header className="mb-4">
                    <h1 className="fw-bolder">Fila de Atendimento - TI</h1>
                    <p className="text-secondary fs-5">Gerencie os chamados de suporte técnico abertos.</p>
                </header>

                <div className="d-grid gap-2 d-md-flex justify-content-md mb-4">
                    <button className="btn btn-primary fw-semibold" type="button" onClick={toggleFormVisibility}>
                        <i className="bi bi-plus-circle me-1"></i>
                        {isFormVisible ? 'Fechar Formulário' : 'Abrir Novo Chamado'}
                    </button>
                </div>
                
                <div className={`collapse ${isFormVisible ? 'show' : ''}`}>
                    <NewTicketForm onTicketSubmit={handleNewTicketSubmit} isSubmitting={isSubmitting} />
                </div>
                
                {/* Lógica para exibir feedback de carregamento ou erro */}
                {isLoading && <div className="text-center p-5"><div className="spinner-border" role="status"><span className="visually-hidden">Carregando...</span></div></div>}
                {error && <div className="alert alert-danger">{error}</div>}
                
                {/* Passa a lista de chamados para o componente que os exibe */}
                {!isLoading && !error && <TicketList tickets={chamados} />}
            </main>
        </>
    );
};

export default ITPage;