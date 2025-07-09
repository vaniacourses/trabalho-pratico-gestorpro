import React, { useState, useEffect, useCallback } from 'react';
import Navbar from '../components/Navbar';
import DataTable from '../components/DataTable';
import DataFilters from '../components/DataFilters';
import GenericForm from '../components/GenericForm';
import finApi from '../services/apiFin';
import type { Column, FilterField } from '../types/Data'; 
import type { FormField } from '../components/GenericForm';

type ChargeStatus = 'PENDENTE' | 'VENCIDA' | 'PAGA' | 'CANCELADA';

interface Charge {
  id: number;
  dataEmissao: Date;
  emailDevedor: string;
  telefone: string;
  valor: number;
  descricao: string;
  dataVencimento: Date;
  status: 'PENDENTE' | 'VENCIDA' | 'PAGA' | 'CANCELADA';
}

const FinPage: React.FC = () => {
//   const { user } = useAuth();
  
  const [charges, setCharges] = useState<Charge[]>([]);
  const [allCharges, setAllCharges] = useState<Charge[]>([]); // Para filtro no cliente
  const [isLoading, setIsLoading] = useState(true);
  const [showAddForm, setShowAddForm] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedCharge, setSelectedCharge] = useState<Charge | null>(null);

  const fetchCharges = useCallback(async () => {
    setIsLoading(true);
    try {
      const response = await finApi.get<Charge[]>('/financeiro/cobrancas');
      const formattedData = response.data.map(charge => ({
        ...charge,
        dataVencimento: new Date(charge.dataVencimento),
        dataEmissao: new Date(charge.dataEmissao),
      }));
      setCharges(formattedData);
      setAllCharges(formattedData);
    } catch (error) {
      console.error("Erro ao buscar cobranças:", error);
    } finally {
      setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchCharges();
  }, [fetchCharges]);

  const handleOpenModal = (charge: Charge) => {
    setSelectedCharge(charge);
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setSelectedCharge(null);
  };

  const handleStatusChange = async (newStatus: 'PAGA' | 'CANCELADA') => {
    if (!selectedCharge) return;
    const { id } = selectedCharge;
    const url = newStatus === 'PAGA' ? `/financeiro/cobrancas/${id}/receber` : `/financeiro/cobrancas/${id}/cancelar`;
    
    try {
      await finApi.put(url);
      await fetchCharges(); // Recarrega a lista
      handleCloseModal();
    } catch (error) {
      console.error(`Erro ao alterar status da cobrança ${id}:`, error);
    }
  };

  const columns: Column<Charge>[] = [
    { header: 'ID', accessorKey: 'id' },
    { header: 'Devedor', accessorKey: 'emailDevedor' },
    { header: 'Valor', cell: (item) => `R$ ${item.valor.toFixed(2)}` },
    { header: 'Vencimento', cell: (item) => item.dataVencimento.toLocaleDateString('pt-BR') },
    { header: 'Status', cell: (item) => {
      const colors = { PENDENTE: 'info', VENCIDA: 'warning', PAGA: 'success', CANCELADA: 'secondary' };
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
    { id: 'emailDevedor', label: 'E-mail do Devedor', type: 'email', placeholder: 'ex: joao.silva@email.com', required: true },
    { id: 'telefone', label: 'Telefone do Devedor', type: 'text', placeholder: 'ex: (21) 99999-9999', required: true },
    { id: 'valor', label: 'Valor da Cobrança (R$)', type: 'number', placeholder: 'ex: 150.50', required: true },
    { id: 'descricao', label: 'Descrição da Dívida', type: 'textarea', placeholder: 'Descreva brevemente a cobrança...', required: true },
    { id: 'dataVencimento', label: 'Data de Vencimento', type: 'date', required: true },
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
  
  const handleAddCharge = async (formData: Record<string, any>) => {
    console.log("Cheguei")
    const requestDto = {
        ...formData,
        valor: parseFloat(formData.valor),
        // dueDate: new Date(formData.dueDate + 'T00:00:00').toISOString()
    };
    try {
      await finApi.post('/financeiro/cobrancas/agendar', requestDto);
      await fetchCharges();
      setShowAddForm(false);
    } catch (error) {
      console.error("Erro ao agendar cobrança:", error);
      alert("Falha ao agendar cobrança.");
    }
  };

  const handleFilter = (filters: Record<string, string>) => {
    let filteredData = allCharges;
    if (filters.id) {
      filteredData = filteredData.filter(c => c.id.toString().includes(filters.id));
    }
    if (filters.status) {
      filteredData = filteredData.filter(c => c.status === filters.status);
    }
    setCharges(filteredData);
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
                  <p><strong>Devedor:</strong> {selectedCharge.emailDevedor}</p>
                  <p><strong>Valor:</strong> R$ {selectedCharge.valor.toFixed(2)}</p>
                  <p className="fw-semibold">Descrição da Dívida:</p>
                  <p className="bg-light p-3 rounded">{selectedCharge.descricao}</p>
                </div>
                <div className="modal-footer">
                  <button type="button" className="btn btn-secondary" onClick={handleCloseModal}>Fechar</button>
                  {(selectedCharge.status === 'PENDENTE' || selectedCharge.status === 'VENCIDA') && (
                    <>
                      <button type="button" className="btn btn-danger" onClick={() => handleStatusChange('CANCELADA')}>Cancelar Cobrança</button>
                      <button type="button" className="btn btn-success" onClick={() => handleStatusChange('PAGA')}>Marcar como Paga</button>
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