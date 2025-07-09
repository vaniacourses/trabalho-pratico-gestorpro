// src/components/TicketList.tsx
import TicketRow, { type TicketAction } from './TITicketRow'; // Importa o tipo TicketAction
import type { Chamado } from '../types/chamado.types';
import type { User } from '../contexts/AuthContext'; // Importa o tipo User

// A interface de props agora inclui o user e a função onActionClick
interface TicketListProps {
  tickets: Chamado[];
  user: User | null;
  onActionClick: (action: TicketAction, ticketId: number) => void;
}

const TicketList: React.FC<TicketListProps> = ({ tickets, user, onActionClick }) => {
    
    return (
        <div className="card p-3">
            {/* O título pode ser dinâmico com base na role do usuário */}
            <h4 className="mb-3 fw-bold">{user?.roles.includes('ROLE_TI') ? 'Todos os Chamados' : 'Meus Chamados'}</h4>
            
            {/* O componente de filtros só aparece para a TI */}
            {user?.roles.includes('ROLE_TI')}

            <div className="table-responsive">
                <table className="table table-hover align-middle bg-white">
                    <thead className="table-light">
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Problema</th>
                            <th scope="col">Urgência</th>
                            <th scope="col">Data</th>
                            <th scope="col">Status</th>
                            <th scope="col" className="text-center">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {tickets.length > 0 ? (
                            tickets.map(ticket => (
                                // Passando as props recebidas para cada linha da tabela
                                <TicketRow 
                                    key={ticket.id} 
                                    ticket={ticket} 
                                    user={user} 
                                    onActionClick={onActionClick} 
                                />
                            ))
                        ) : (
                            <tr>
                                <td colSpan={6} className="text-center p-4">Nenhum chamado encontrado.</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default TicketList;