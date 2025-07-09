import React, { useState, useEffect, useCallback } from 'react';
import Navbar from '../components/Navbar';
import DataTable from '../components/DataTable';
import DataFilters from '../components/DataFilters';
import GenericForm from '../components/GenericForm';
import type { Column, FilterField } from '../types/Data'; // Supondo que seus tipos estejam aqui
import type { FormField } from '../components/GenericForm';

// 1. DEFINIÇÃO DOS TIPOS DE DADOS
type ChargeStatus = 'Pendente' | 'Vencida' | 'Paga' | 'Cancelada';

interface Charge {
  id: number;
  debtorEmail: string;
  debtorPhone: string;
  amount: number;
  description: string;
  dueDate: string;
  status: ChargeStatus;
}

// 2. DADOS MOCKADOS (simulando uma chamada de API)
const mockCharges: Charge[] = [
  { id: 101, debtorEmail: 'ana.clara@email.com', debtorPhone: '21998765432', amount: 1500.50, description: 'Serviço de consultoria', dueDate: '2025-07-15', status: 'Pendente' },
  { id: 102, debtorEmail: 'carlos.souza@email.com', debtorPhone: '11912345678', amount: 850.00, description: 'Desenvolvimento de logo', dueDate: '2025-06-30', status: 'Vencida' },
  { id: 103, debtorEmail: 'mariana.lima@email.com', debtorPhone: '31987654321', amount: 3200.00, description: 'Manutenção de sistema', dueDate: '2025-07-01', status: 'Paga' },
  { id: 104, debtorEmail: 'pedro.mota@email.com', debtorPhone: '41999998888', amount: 450.75, description: 'Licença de software', dueDate: '2025-08-01', status: 'Pendente' },
  { id: 105, debtorEmail: 'beatriz.santos@email.com', debtorPhone: '51988887777', amount: 200.00, description: 'Taxa de serviço', dueDate: '2025-05-20', status: 'Cancelada' },
];

const FinPage: React.FC = () => {
  const [charges, setCharges] = useState<Charge[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [showAddForm, setShowAddForm] = useState(false);

  useEffect(() => {
    // Simula o carregamento dos dados iniciais
    setTimeout(() => {
      setCharges(mockCharges);
      setIsLoading(false);
    }, 1000);
  }, []);
  
  // 3. CONFIGURAÇÕES DOS COMPONENTES
  
  // Configuração para o formulário de nova cobrança
  const chargeFormFields: FormField[] = [
    { id: 'debtorEmail', label: 'E-mail do Devedor', type: 'email', placeholder: 'ex: joao.silva@email.com', required: true },
    { id: 'debtorPhone', label: 'Telefone do Devedor', type: 'text', placeholder: 'ex: (21) 99999-9999', required: true },
    { id: 'amount', label: 'Valor da Cobrança (R$)', type: 'number', placeholder: 'ex: 150.50', required: true },
    { id: 'description', label: 'Descrição da Dívida', type: 'textarea', placeholder: 'Descreva brevemente a cobrança...', required: true },
    { id: 'dueDate', label: 'Data de Vencimento', type: 'date', required: true },
  ];

  // Configuração para os filtros da tabela
  const chargeFilters: FilterField[] = [
    { id: 'id', type: 'text', placeholder: 'Buscar por ID...' },
    { id: 'status', type: 'select', options: [
      { value: '', label: 'Todos os Status' },
      { value: 'Pendente', label: 'Pendente' },
      { value: 'Vencida', label: 'Vencida' },
      { value: 'Paga', label: 'Paga' },
      { value: 'Cancelada', label: 'Cancelada' },
    ]},
  ];
  
  // Configuração para as colunas da tabela
  const columns: Column<Charge>[] = [
    { header: 'ID', accessorKey: 'id' },
    { header: 'Devedor', accessorKey: 'debtorEmail' },
    { header: 'Valor', cell: (item) => `R$ ${item.amount.toFixed(2)}` },
    { header: 'Vencimento', accessorKey: 'dueDate' },
    { header: 'Status', cell: (item) => {
      const colors = { Pendente: 'info', Vencida: 'warning', Paga: 'success', Cancelada: 'secondary' };
      return <span className={`badge bg-${colors[item.status]}`}>{item.status}</span>;
    }},
    { header: 'Ações', cell: (item) => (
      (item.status === 'Pendente' || item.status === 'Vencida') && (
        <div className="btn-group btn-group-sm">
          <button className="btn btn-outline-success" onClick={() => handleStatusChange(item.id, 'Paga')}>Paga</button>
          <button className="btn btn-outline-danger" onClick={() => handleStatusChange(item.id, 'Cancelada')}>Cancelar</button>
        </div>
      )
    )},
  ];

  // 4. FUNÇÕES DE MANIPULAÇÃO DE DADOS

  const handleAddCharge = (formData: Record<string, any>) => {
    console.log('Nova Cobrança:', formData);
    const newCharge: Charge = {
      id: Math.max(...charges.map(c => c.id)) + 1,
      debtorEmail: formData.debtorEmail,
      debtorPhone: formData.debtorPhone,
      amount: parseFloat(formData.amount),
      description: formData.description,
      dueDate: formData.dueDate,
      status: 'Pendente'
    };
    setCharges(prev => [newCharge, ...prev]);
    setShowAddForm(false); // Esconde o formulário após o envio
  };

  const handleFilter = (filters: Record<string, string>) => {
    console.log('Filtros aplicados:', filters);
    // Lógica de filtragem (em uma aplicação real, faria uma nova chamada de API)
    setIsLoading(true);
    let filteredData = mockCharges;
    if (filters.id) {
      filteredData = filteredData.filter(c => c.id.toString().includes(filters.id));
    }
    if (filters.status) {
      filteredData = filteredData.filter(c => c.status === filters.status);
    }
    setTimeout(() => {
      setCharges(filteredData);
      setIsLoading(false);
    }, 500);
  };
  
  const handleStatusChange = (id: number, newStatus: 'Paga' | 'Cancelada') => {
    setCharges(prev => prev.map(charge => 
      charge.id === id ? { ...charge, status: newStatus } : charge
    ));
  };
  
  // 5. RENDERIZAÇÃO DO COMPONENTE
  return (
    <>
      <Navbar />
      <main className="container mt-4">
        <header className="d-flex justify-content-between align-items-center mb-4">
          <div>
            <h1 className="fw-bolder">Módulo Financeiro</h1>
            <p className="text-secondary fs-5">Gerencie as cobranças e o fluxo de caixa.</p>
          </div>
          <button className="btn btn-success" onClick={() => setShowAddForm(!showAddForm)}>
            <i className="bi bi-plus-circle-fill me-2"></i>
            {showAddForm ? 'Fechar Formulário' : 'Adicionar Cobrança'}
          </button>
        </header>

        {showAddForm && (
          <GenericForm 
            title="Adicionar Nova Cobrança"
            fields={chargeFormFields}
            submitButtonText="Salvar Cobrança"
            onSubmit={handleAddCharge}
          />
        )}
        
        <DataTable
          title="Fila de Cobranças"
          columns={columns}
          data={charges}
          isLoading={isLoading}
          filterComponent={<DataFilters config={chargeFilters} onFilter={handleFilter} />}
        />
      </main>
    </>
  );
};

export default FinPage;