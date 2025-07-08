import '../components/IT.css';
import { useState } from 'react';
import Navbar from '../components/Navbar';
import NewTicketForm from '../components/NewTicketForm';
import type { FormDataState } from '../components/NewTicketForm'; 
import TicketList from '../components/TicketList';

const ITPage: React.FC = () => {
    const [isFormVisible, setIsFormVisible] = useState(false);

    const handleNewTicketSubmit = (ticketData: FormDataState) => {
        console.log("Dados:", ticketData);

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
                    <NewTicketForm onTicketSubmit={handleNewTicketSubmit} />
                </div>
                <TicketList/>
            </main>
        </>
    );
};

export default ITPage;