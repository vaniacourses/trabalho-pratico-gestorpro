import React, { useState, useEffect, useCallback } from 'react';
import Navbar from '../components/Navbar';
import DataTable from '../components/DataTable';
import DataFilters from '../components/DataFilters';
import GenericForm from '../components/GenericForm';
import type { Column, FilterField } from '../types/Data'; 
import type { FormField } from '../components/GenericForm';

type ChargeStatus = 'Pendente' | 'Vencida' | 'Paga' | 'Cancelada';

interface Charge {
  id: number;
  debtorEmail: string;
  debtorPhone: string;
  amount: number;
  description: string;
  dueDate: Date;
  status: ChargeStatus;
}

const mockCharges: Charge[] = [
    { id: 101, debtorEmail: 'ana.clara@email.com', debtorPhone: '21998765432', amount: 1500.50, description: 'Desenvolvimento e implementação do módulo de pagamentos do sistema de e-commerce, incluindo integração com gateways de pagamento e testes de segurança.', dueDate: new Date('2025-07-15T00:00:00'), status: 'Pendente' },
    { id: 102, debtorEmail: 'carlos.souza@email.com', debtorPhone: '11912345678', amount: 850.00, description: 'Criação de identidade visual completa para a nova marca "Café do Ponto", incluindo logo, paleta de cores e manual da marca.', dueDate: new Date('2025-06-30T00:00:00'), status: 'Vencida' },
    { id: 103, debtorEmail: 'mariana.lima@email.com', debtorPhone: '31987654321', amount: 3200.00, description: 'Contrato de manutenção mensal para o servidor principal, cobrindo atualizações de segurança, backups e suporte técnico 24/7.', dueDate: new Date('2025-07-01T00:00:00'), status: 'Paga' },
    { id: 104, debtorEmail: 'pedro.mota@email.com', debtorPhone: '41999998888', amount: 450.75, description: 'Licença anual para o software de edição de vídeo "EditPro Plus".', dueDate: new Date('2025-08-01T00:00:00'), status: 'Pendente' },
    { id: 105, debtorEmail: 'beatriz.santos@email.com', debtorPhone: '51988887777', amount: 200.00, description: 'Taxa de serviço para cancelamento de assinatura fora do prazo contratual.', dueDate: new Date('2025-05-20T00:00:00'), status: 'Cancelada' },
];

const FinPage: React.FC = () => {
  const [charges, setCharges] = useState<Charge[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [showAddForm, setShowAddForm] = useState(false);
  
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedCharge, setSelectedCharge] = useState<Charge | null>(null);

  useEffect(() => {
    setTimeout(() => {
      setCharges(mockCharges);
      setIsLoading(false);
    }, 1000);
  }, []);


  const handleOpenModal = (charge: Charge) => {
    setSelectedCharge(charge);
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setSelectedCharge(null);
  };

  const handleStatusChange = (newStatus: 'Paga' | 'Cancelada') => {
    if (!selectedCharge) return;

    setCharges(prev => prev.map(charge => 
      charge.id === selectedCharge.id ? { ...charge, status: newStatus } : charge
    ));
    handleCloseModal();
  };

  const columns: Column<Charge>[] = [
    { header: 'ID', accessorKey: 'id' },
    { header: 'Devedor', accessorKey: 'debtorEmail' },
    { header: 'Valor', cell: (item) => `R$ ${item.amount.toFixed(2)}` },
    { header: 'Vencimento', cell: (item) => item.dueDate.toLocaleDateString('pt-BR') },
    { header: 'Status', cell: (item) => {
      const colors = { Pendente: 'info', Vencida: 'warning', Paga: 'success', Cancelada: 'secondary' };
      return <span className={`badge bg-${colors[item.status]}`}>{item.status}</span>;
    }},
    { header: 'Ações', cell: (item) => (
      <button 
        className="btn btn-sm btn-outline-primary"
        onClick={() => handleOpenModal(item)}
      >
        Detalhes
      </button>
    )},
  ];

  const chargeFormFields: FormField[] = [
    { id: 'debtorEmail', label: 'E-mail do Devedor', type: 'email', placeholder: 'ex: joao.silva@email.com', required: true },
    { id: 'debtorPhone', label: 'Telefone do Devedor', type: 'text', placeholder: 'ex: (21) 99999-9999', required: true },
    { id: 'amount', label: 'Valor da Cobrança (R$)', type: 'number', placeholder: 'ex: 150.50', required: true },
    { id: 'description', label: 'Descrição da Dívida', type: 'textarea', placeholder: 'Descreva brevemente a cobrança...', required: true },
    { id: 'dueDate', label: 'Data de Vencimento', type: 'date', required: true },
  ];
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
  const handleAddCharge = (formData: Record<string, any>) => {
    const newCharge: Charge = { id: Math.max(...charges.map(c => c.id)) + 1, debtorEmail: formData.debtorEmail, debtorPhone: formData.debtorPhone, amount: parseFloat(formData.amount), description: formData.description, dueDate: formData.dueDate, status: 'Pendente' };
    setCharges(prev => [newCharge, ...prev]);
    setShowAddForm(false);
  };
  const handleFilter = (filters: Record<string, string>) => {
    setIsLoading(true);
    let filteredData = mockCharges;
    if (filters.id) filteredData = filteredData.filter(c => c.id.toString().includes(filters.id));
    if (filters.status) filteredData = filteredData.filter(c => c.status === filters.status);
    setTimeout(() => { setCharges(filteredData); setIsLoading(false); }, 500);
  };

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

        {showAddForm && <GenericForm title="Adicionar Nova Cobrança" fields={chargeFormFields} submitButtonText="Salvar Cobrança" onSubmit={handleAddCharge} />}
        
        <DataTable
          title="Fila de Cobranças"
          columns={columns}
          data={charges}
          isLoading={isLoading}
          filterComponent={<DataFilters config={chargeFilters} onFilter={handleFilter} />}
        />
      </main>

      {isModalOpen && selectedCharge && (
        <>
          <div className="modal-backdrop fade show"></div>
          <div className="modal fade show" style={{ display: 'block' }} tabIndex={-1}>
            <div className="modal-dialog modal-dialog-centered">
              <div className="modal-content">
                <div className="modal-header">
                  <h5 className="modal-title">Detalhes da Cobrança #{selectedCharge.id}</h5>
                  <button type="button" className="btn-close" onClick={handleCloseModal}></button>
                </div>
                <div className="modal-body">
                  <p><strong>Devedor:</strong> {selectedCharge.debtorEmail}</p>
                  <p><strong>Valor:</strong> R$ {selectedCharge.amount.toFixed(2)}</p>
                  <p className="fw-semibold">Descrição da Dívida:</p>
                  <p className="bg-light p-3 rounded">{selectedCharge.description}</p>
                </div>
                <div className="modal-footer">
                  <button type="button" className="btn btn-secondary" onClick={handleCloseModal}>Fechar</button>
                  {(selectedCharge.status === 'Pendente' || selectedCharge.status === 'Vencida') && (
                    <>
                      <button type="button" className="btn btn-danger" onClick={() => handleStatusChange('Cancelada')}>Cancelar Cobrança</button>
                      <button type="button" className="btn btn-success" onClick={() => handleStatusChange('Paga')}>Marcar como Paga</button>
                    </>
                  )}
                </div>
              </div>
            </div>
          </div>
        </>
      )}
    </>
  );
};

export default FinPage;