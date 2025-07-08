import React, { useState } from 'react';

// definir tipos para os filtros

interface TicketFiltersProps {
    onFilter: (filters: { searchTerm: string; status: string; priority: string }) => void;
}

const TicketFilters: React.FC<TicketFiltersProps> = ({ onFilter }) => {
    const [searchTerm, setSearchTerm] = useState('');
    const [status, setStatus] = useState('Todos os Status');
    const [priority, setPriority] = useState('Qualquer Prioridade');
    
    const handleFilterClick = () => {
        onFilter({ searchTerm, status, priority });
    };

    return (
        <div className="row g-2 mb-3">
            <div className="col-md-5">
                <input 
                    type="text" 
                    className="form-control" 
                    placeholder="Buscar por título ou ID..."
                    value={searchTerm}
                    onChange={e => setSearchTerm(e.target.value)}
                />
            </div>
            {/* ... outros selects para status e prioridade ... */}
            <div className="col-md-1 d-grid">
                <button className="btn btn-secondary" onClick={handleFilterClick}>Filtrar</button>
            </div>
        </div>
    );
};

export default TicketFilters;