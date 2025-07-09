import React, { useState, useEffect, useMemo } from 'react';
import DataTable from '../components/DataTable';
import DataFilters from '../components/DataFilters';
import type { Column, FilterField } from '../types/Data';

type TicketStatus = 'Aberto' | 'Em Andamento' | 'Fechado';
type TicketPriority = 'Alta' | 'Média' | 'Baixa';

interface Ticket {
  id: number;
  priority: TicketPriority;
  title: string;
  requester: string;
  date: string;
  status: TicketStatus;
}

const PriorityBadge: React.FC<{ priority: TicketPriority }> = ({ priority }) => {
  const colorMap: Record<TicketPriority, string> = { 'Alta': 'bg-danger', 'Média': 'bg-warning text-dark', 'Baixa': 'bg-success' };
  return <span className={`badge ${colorMap[priority]}`}>{priority}</span>;
};

const StatusBadge: React.FC<{ status: TicketStatus }> = ({ status }) => {
  const colorMap: Record<TicketStatus, string> = { 'Aberto': 'bg-info text-dark', 'Em Andamento': 'bg-secondary', 'Fechado': 'bg-light text-dark' };
  return <span className={`badge ${colorMap[status]}`}>{status}</span>;
};

const mockTickets: Ticket[] = [
  { id: 1, priority: 'Alta', title: 'Sistema de Faturamento fora do ar', requester: 'Ana Clara', date: '08/07/2025', status: 'Aberto' },
  { id: 2, priority: 'Média', title: 'Não consigo acessar a impressora', requester: 'Carlos Souza', date: '07/07/2025', status: 'Aberto' },
  { id: 3, priority: 'Baixa', title: 'Solicitação de novo mouse', requester: 'Mariana Lima', date: '07/07/2025', status: 'Em Andamento' },
  { id: 4, priority: 'Alta', title: 'Servidor principal não responde', requester: 'Pedro Mota', date: '08/07/2025', status: 'Em Andamento' },
];

const TicketList: React.FC = () => {
  const [allTickets, setAllTickets] = useState<Ticket[]>([]);
  const [filters, setFilters] = useState({ searchTerm: '', status: 'Todos', priority: 'Todas' });
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    setIsLoading(true);
    setTimeout(() => {
      setAllTickets(mockTickets);
      setIsLoading(false);
    }, 500);
  }, []);

  const filteredTickets = useMemo(() => {
    return allTickets.filter(ticket => {
      const searchMatch = ticket.title.toLowerCase().includes(filters.searchTerm.toLowerCase()) || ticket.id.toString().includes(filters.searchTerm);
      const statusMatch = filters.status === 'Todos' || ticket.status === filters.status;
      const priorityMatch = filters.priority === 'Todas' || ticket.priority === filters.priority;
      return searchMatch && statusMatch && priorityMatch;
    });
  }, [allTickets, filters]);

  const handleFilterUpdate = (newFilterValues: Record<string, string>) => {
    setFilters(currentFilters => ({
        ...currentFilters,
        ...newFilterValues,
    }));
  };

  const columns: Column<Ticket>[] = [
    { header: 'Prioridade', cell: (ticket) => <PriorityBadge priority={ticket.priority} /> },
    { header: 'Título', accessorKey: 'title' },
    { header: 'Solicitante', accessorKey: 'requester' },
    { header: 'Data', accessorKey: 'date' },
    { header: 'Status', cell: (ticket) => <StatusBadge status={ticket.status} /> },
    { header: 'Ações', cell: () => <a href="#" className="btn btn-sm btn-outline-primary">Detalhes</a> },
  ];

  const filterConfig: FilterField[] = [
    { id: 'searchTerm', type: 'text', placeholder: 'Buscar por título ou ID...' },
    { id: 'status', type: 'select', options: [{value: 'Todos', label: 'Todos os Status'}, {value: 'Aberto', label: 'Aberto'}, {value: 'Em Andamento', label: 'Em Andamento'}] },
    { id: 'priority', type: 'select', options: [{value: 'Todas', label: 'Qualquer Prioridade'}, {value: 'Alta', label: 'Alta'}, {value: 'Média', label: 'Média'}, {value: 'Baixa', label: 'Baixa'}] },
  ];

  return (
    <div className="container mt-4">
      <DataTable
        title="Fila de Atendimento - TI"
        data={filteredTickets}
        columns={columns}
        isLoading={isLoading}
        filterComponent={<DataFilters config={filterConfig} onFilter={handleFilterUpdate} />}
      />
    </div>
  );
};

export default TicketList;