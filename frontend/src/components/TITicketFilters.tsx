import React, { useState } from 'react';
import { type Status, ALL_STATUSES } from '../types/chamado.types';

interface TicketFiltersProps {
    onFilter: (status: Status | 'Todos os Status') => void;
}

const TicketFilters: React.FC<TicketFiltersProps> = ({ onFilter }) => {
    const [selectedStatus, setSelectedStatus] = useState<Status | 'Todos os Status'>('Todos os Status');
    
    const handleStatusChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const newStatus = e.target.value as Status | 'Todos os Status';
        setSelectedStatus(newStatus);
        onFilter(newStatus); 
    };

    return (
        <div className="row g-2 mb-3 align-items-center">
            <div className="col-md-3">
                <label htmlFor="statusFilter" className="form-label visually-hidden">Filtrar por Status</label>
                <select 
                    id="statusFilter"
                    className="form-select"
                    value={selectedStatus}
                    onChange={handleStatusChange}
                >
                    <option value="Todos os Status">Todos os Status</option>
                    {ALL_STATUSES.map(s => (
                        <option 
                            key={s} 
                            value={s} 
                        >
                            {s.replace(/_/g, ' ')}
                        </option>
                    ))}
                </select>
            </div>
        </div>
    );
};

export default TicketFilters;