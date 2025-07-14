// src/components/DataFilters.tsx
import React, { useState } from 'react';
import type { FilterField } from '../types/Data';

interface DataFiltersProps {
  config: FilterField[];
  onFilter: (filters: Record<string, string>) => void;
}

const DataFilters: React.FC<DataFiltersProps> = ({ config, onFilter }) => {
  const initialState = config.reduce((acc, field) => {
    acc[field.id] = '';
    return acc;
  }, {} as Record<string, string>);

  const [filters, setFilters] = useState(initialState);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    setFilters(prev => ({ ...prev, [e.target.id]: e.target.value }));
  };

  return (
    <div className="row g-2 mb-4 align-items-end">
      {config.map(field => (
        <div className="col-md" key={field.id}>
          {field.type === 'text' && (
            <input
              type="text"
              id={field.id}
              className="form-control"
              placeholder={field.placeholder}
              value={filters[field.id]}
              onChange={handleChange}
            />
          )}
          {field.type === 'select' && (
            <select
              id={field.id}
              className="form-select"
              value={filters[field.id]}
              onChange={handleChange}
            >
              {field.options?.map(opt => (
                <option key={opt.value} value={opt.value}>{opt.label}</option>
              ))}
            </select>
          )}
        </div>
      ))}
      <div className="col-md-auto d-grid">
        <button className="btn btn-secondary" onClick={() => onFilter(filters)}>
          Filtrar
        </button>
      </div>
    </div>
  );
};

export default DataFilters;