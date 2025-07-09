import React from 'react';

export interface Column<T> {
  header: string;
  accessorKey?: keyof T;
  cell?: (item: T) => React.ReactNode;
}

export interface FilterField {
  id: string;
  type: 'text' | 'select';
  placeholder?: string;
  options?: { value: string; label:string }[];
}