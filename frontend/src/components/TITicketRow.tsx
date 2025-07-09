import React from 'react';
import type { Chamado } from '../types/chamado.types';
import type { User } from '../contexts/AuthContext';
import { Link } from 'react-router-dom';

const UrgencyBadge: React.FC<{ urgency: string }> = ({ urgency }) => {
  const colorMap: { [key: string]: string } = {
    'Alta': 'bg-danger', 'Média': 'bg-warning text-dark', 'Baixa': 'bg-success',
  };
  return <span className={`badge ${colorMap[urgency] || 'bg-secondary'}`}>{urgency}</span>;
};

export const StatusBadge: React.FC<{ status: string }> = ({ status }) => {
    const colorMap: { [key: string]: string } = {
      'ABERTO': 'bg-primary', 'EM_ANDAMENTO': 'bg-warning text-dark', 'RESOLVIDO': 'bg-success',
      'CANCELADO': 'bg-secondary', 'FECHADO': 'bg-dark',
    };
    const formattedStatus = status.replace('_', ' ');
    return <span className={`badge ${colorMap[status] || 'bg-light text-dark'}`}>{formattedStatus}</span>;
};


// Definimos o tipo de ação que pode ser feita
export type TicketAction = 'iniciar' | 'resolver' | 'cancelar' | 'reabrir' | 'fechar' | 'detalhes';

interface TicketRowProps {
  ticket: Chamado;
  user: User | null; // Recebe o objeto do usuário logado
  onActionClick: (action: TicketAction, ticketId: number) => void; // Recebe uma função para lidar com os cliques
}

const TicketRow: React.FC<TicketRowProps> = ({ ticket, user, onActionClick }) => {
  // --- Lógica de Permissão ---
  const isTI = user?.roles.includes('ROLE_TI') ?? false;
  const isOwner = user?.email === ticket.solicitanteEmail;
  const { status, id } = ticket;

  const formattedDate = new Date(ticket.dataHoraCriacao).toLocaleDateString('pt-BR');

  return (
    <tr>
      <th scope="row">#{id}</th>
      <td>{ticket.tipoProblema}</td>
      <td><UrgencyBadge urgency={ticket.urgencia} /></td>
      <td>{formattedDate}</td>
      <td><StatusBadge status={status} /></td>
      
      {/* Coluna de Ações com botões renderizados condicionalmente */}
      <td className="text-center">
        <div className="btn-group btn-group-sm" role="group">
          
          {/* Botão Iniciar: Apenas para TI e se o chamado estiver ABERTO */}
          {isTI && status === 'ABERTO' && (
            <button className="btn btn-outline-success" onClick={() => onActionClick('iniciar', id)}>Iniciar</button>
          )}

          {/* Botão Resolver: Apenas para TI e se estiver EM ANDAMENTO */}
          {isTI && status === 'EM_ANDAMENTO' && (
            <button className="btn btn-outline-success" onClick={() => onActionClick('resolver', id)}>Resolver</button>
          )}

          {/* Botão Cancelar: Para o dono OU para TI, se estiver ABERTO ou EM ANDAMENTO */}
          {(isOwner || isTI) && (status === 'ABERTO' || status === 'EM_ANDAMENTO') && (
            <button className="btn btn-outline-warning" onClick={() => onActionClick('cancelar', id)}>Cancelar</button>
          )}

          {/* Botão Reabrir: Apenas para TI, se estiver RESOLVIDO ou CANCELADO */}
          {isTI && (status === 'RESOLVIDO' || status === 'CANCELADO') && (
            <button className="btn btn-outline-info" onClick={() => onActionClick('reabrir', id)}>Reabrir</button>
          )}

          {/* Botão Fechar: Apenas para TI, se o chamado não estiver FECHADO */}
          {isTI && status !== 'FECHADO' && (
            <button className="btn btn-outline-danger" onClick={() => onActionClick('fechar', id)}>Fechar</button>
          )}
          {(isOwner || isTI) && (
          <Link to={`/ti/${ticket.id}`} className="btn btn-outline-primary">Detalhes</Link>
          )}
        </div>
      </td>
    </tr>
  );
};

export default TicketRow;