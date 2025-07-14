// src/components/DataTable.tsx
import React from 'react';
import type { Column } from '../types/Data';

// Usamos Generics do TypeScript (<T>) para que o componente aceite qualquer tipo de dado
interface DataTableProps<T> {
  title: string;
  data: T[];
  columns: Column<T>[];
  isLoading: boolean;
  // Prop para renderizar um componente de filtro acima da tabela
  filterComponent?: React.ReactNode; 
}

function DataTable<T extends { id: number | string }>({
  title,
  data,
  columns,
  isLoading,
  filterComponent,
}: DataTableProps<T>) {

  if (isLoading) {
    return <p className="text-center p-5">Carregando...</p>;
  }

  return (
    <div className="card p-3 shadow-sm">
      <h4 className="mb-3 fw-bold">{title}</h4>
      
      {filterComponent}
      
      <div className="table-responsive">
        <table className="table table-hover align-middle bg-white">
          <thead className="table-light">
            <tr>
              {columns.map(col => <th scope="col" key={col.header}>{col.header}</th>)}
            </tr>
          </thead>
          <tbody>
            {data.length > 0 ? (
              data.map(item => (
                <tr key={item.id}>
                  {columns.map(col => (
                    <td key={`${item.id}-${col.header}`}>
                      {/* Renderiza a célula com a função 'cell' se ela existir, senão, usa o accessorKey */}
                      {col.cell
                        ? col.cell(item)
                        : col.accessorKey
                        ? String(item[col.accessorKey])
                        : null}
                    </td>
                  ))}
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={columns.length} className="text-center p-4">
                  Nenhum item encontrado.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default DataTable;