import React from 'react';
import type { Ticket } from '../types/Ticket';

const PriorityBadge: React.FC<{ priority: Ticket['priority'] }> = ({ priority }) => {
  const colorMap = {
    'Alta': 'bg-danger',
    'Média': 'bg-warning text-dark',
    'Baixa': 'bg-success',
  };
  return <span className={`badge ${colorMap[priority]}`}>{priority}</span>;
};

const StatusBadge: React.FC<{ status: Ticket['status'] }> = ({ status }) => {
    const colorMap = {
      'Aberto': 'bg-info text-dark',
      'Em Andamento': 'bg-secondary',
      'Fechado': 'bg-light text-dark',
    };
    return <span className={`badge ${colorMap[status]}`}>{status}</span>;
  };


interface TicketRowProps {
  ticket: Ticket;
}

const TicketRow: React.FC<TicketRowProps> = ({ ticket }) => {
  return (
    <tr>
      <td><PriorityBadge priority={ticket.priority} /></td>
      <td>{ticket.title}</td>
      <td>{ticket.requester}</td>
      <td>{ticket.date}</td>
      <td><StatusBadge status={ticket.status} /></td>
      <td className="text-center">
        <a href="#" className="btn btn-sm btn-outline-primary">Detalhes</a>
      </td>
    </tr>
  );
};

export default TicketRow;