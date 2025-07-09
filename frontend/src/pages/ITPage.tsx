import '../components/global.css';
import { useState } from 'react';
import Navbar from '../components/Navbar';
import TicketList from '../components/TicketList';
import GenericForm from '../components/GenericForm';
import type { FormField } from '../components/GenericForm';

const ITPage: React.FC = () => {
    const [isFormVisible, setIsFormVisible] = useState(false);
    const ticketFields: FormField[] = [
        {
            id: 'title',
            label: 'Título do Chamado',
            type: 'text',
            placeholder: 'Ex: Não consigo acessar a impressora',
            required: true,
        },
        {
            id: 'category',
            label: 'Categoria',
            type: 'select',
            required: true,
            options: [
                { value: 'software', label: 'Software' },
                { value: 'hardware', label: 'Hardware' },
                { value: 'network', label: 'Rede e Internet' },
                { value: 'access', label: 'Acessos e Permissões' },
                { value: 'other', label: 'Outro' },
            ],
        },
        {
            id: 'description',
            label: 'Descrição do Problema',
            type: 'textarea',
            placeholder: 'Detalhe aqui o problema que você está enfrentando...',
            rows: 4,
            required: true,
        },
        {
            id: 'attachment',
            label: 'Anexar Arquivo (Opcional)',
            type: 'file',
            helpText: 'Anexe prints ou documentos que ajudem a descrever o problema.',
        },
    ];

    const handleNewTicketSubmit = (formData: Record<string, any>) => {
        console.log("Dados:", formData);

        // Chamada a API
        setIsFormVisible(false); 
    };

    const toggleFormVisibility = () => {
        setIsFormVisible(currentValue => !currentValue);
    };

    return (
        <>
            <Navbar/>

            <main className="container mt-4 mb-5">
        
                <header className="mb-4">
                    <h1 className="fw-bolder">Fila de Atendimento - TI</h1>
                    <p className="text-secondary fs-5">Gerencie os chamados de suporte técnico abertos.</p>
                </header>

                <div className="d-grid gap-2 d-md-flex justify-content-md mb-4">
                    <button className="btn btn-primary fw-semibold" type="button" onClick={toggleFormVisibility}>
                        <i className="bi bi-plus-circle me-1"></i>
                        {isFormVisible ? 'Fechar Formulário' : 'Abrir Novo Chamado'}
                    </button>
                </div>
                <div className={`collapse ${isFormVisible ? 'show' : ''}`}>
                    <GenericForm
                        title="Abrir Novo Chamado"
                        fields={ticketFields}
                        submitButtonText="Enviar Chamado"
                        onSubmit={handleNewTicketSubmit}
                    />
                </div>
                <TicketList/>
            </main>
        </>
    );
};

export default ITPage;