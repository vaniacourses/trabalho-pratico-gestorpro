// src/components/TicketList.tsx
import TicketRow from './TicketRow';
import TicketFilters from './TicketFilters';
import { Chamado } from '../pages/ITPage'; // Importa a interface do tipo Chamado

// A interface define que este componente espera receber um array de 'Chamado'
interface TicketListProps {
  tickets: Chamado[];
}

const TicketList: React.FC<TicketListProps> = ({ tickets }) => {
    
    const handleFilter = (filters: any) => {
        console.log('Filtrando por:', filters);
        // A lógica de filtro real seria implementada aqui, fazendo uma nova chamada à API
    };

    return (
        <div className="card p-3">
            <h4 className="mb-3 fw-bold">Meus Chamados</h4>
            
            <TicketFilters onFilter={handleFilter} />

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
                        {/* Verifica se a lista de chamados tem itens.
                          Se tiver, mapeia cada item para um componente TicketRow.
                          Se não, mostra uma mensagem de "Nenhum chamado encontrado".
                        */}
                        {tickets.length > 0 ? (
                            tickets.map(ticket => (
                                <TicketRow key={ticket.id} ticket={ticket} />
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