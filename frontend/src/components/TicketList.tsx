// src/components/TicketList.tsx
import React, { useState, useEffect } from 'react';
import type { Ticket } from '../types/Ticket';
import TicketRow from './TicketRow';
import TicketFilters from './TicketFilters';

// Dados mocados para simular uma API
const mockTickets: Ticket[] = [
    { id: 1, priority: 'Alta', title: 'Sistema de Faturamento fora do ar', requester: 'Ana Clara', date: '08/07/2025', status: 'Aberto' },
    { id: 2, priority: 'Média', title: 'Não consigo acessar a impressora', requester: 'Carlos Souza', date: '07/07/2025', status: 'Aberto' },
    { id: 3, priority: 'Baixa', title: 'Solicitação de novo mouse', requester: 'Mariana Lima', date: '07/07/2025', status: 'Em Andamento' },
];

const TicketList: React.FC = () => {
    const [tickets, setTickets] = useState<Ticket[]>([]);
    const [isLoading, setIsLoading] = useState(true);

    // Simula a busca de dados quando o componente é montado
    useEffect(() => {
        setIsLoading(true);
        // Simula uma chamada de API com um atraso de 1 segundo
        const timer = setTimeout(() => {
            setTickets(mockTickets);
            setIsLoading(false);
        }, 1000);

        return () => clearTimeout(timer);
    }, []);

    const handleFilter = (filters: any) => {
        console.log('Filtrando por:', filters);
        // Aqui você implementaria a lógica de filtragem real
    }

    if (isLoading) {
        return <p className="text-center">Carregando chamados...</p>;
    }

    return (
        <div className="card p-3">
            <h4 className="mb-3 fw-bold">Chamados Aguardando Atendimento</h4>
            
            <TicketFilters onFilter={handleFilter} />

            <div className="table-responsive">
                <table className="table table-hover align-middle bg-white">
                    <thead className="table-light">
                        <tr>
                            <th scope="col">Prioridade</th>
                            <th scope="col">Título</th>
                            <th scope="col">Solicitante</th>
                            <th scope="col">Data</th>
                            <th scope="col">Status</th>
                            <th scope="col" className="text-center">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {tickets.length > 0 ? (
                            tickets.map(ticket => (
                                <TicketRow key={ticket.id} ticket={ticket} />
                            ))
                        ) : (
                            <tr>
                                <td colSpan={6} className="text-center">Nenhum chamado encontrado.</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default TicketList;