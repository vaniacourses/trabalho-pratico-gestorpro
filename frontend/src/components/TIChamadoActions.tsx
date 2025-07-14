import React from 'react';
import type { Chamado } from '../types/chamado.types';
import type { TicketAction } from './TITicketRow';

interface ChamadoActionsProps {
  chamado: Chamado;
  onActionClick: (action: TicketAction, ticketId: number) => void;
}

const ChamadoActions: React.FC<ChamadoActionsProps> = ({ chamado, onActionClick }) => {
  const { status, id } = chamado;

  return (
    <div className="mt-4 pt-4 border-top">
      <h5 className="fw-bold mb-3">Ações de TI</h5>
      <div className="btn-group" role="group" aria-label="Ações de TI">
        
        {status === 'ABERTO' && (
          <button className="btn btn-success" onClick={() => onActionClick('iniciar', id)}>Iniciar Atendimento</button>
        )}

        {status === 'EM_ANDAMENTO' && (
          <button className="btn btn-success" onClick={() => onActionClick('resolver', id)}>Resolver Chamado</button>
        )}

        {status === 'ABERTO' || status === 'EM_ANDAMENTO' ? (
          <button className="btn btn-warning" onClick={() => onActionClick('cancelar', id)}>Cancelar</button>
        ) : null}

        {status === 'RESOLVIDO' || status === 'CANCELADO' ? (
          <button className="btn btn-info" onClick={() => onActionClick('reabrir', id)}>Reabrir Chamado</button>
        ) : null}

        {status !== 'FECHADO' && (
          <button className="btn btn-danger" onClick={() => onActionClick('fechar', id)}>Fechar Chamado</button>
        )}
      </div>
    </div>
  );
};

export default ChamadoActions;