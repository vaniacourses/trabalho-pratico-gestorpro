import { useState } from 'react';
import type { ChangeEvent } from 'react'; 
import './Navbar.css';

export interface FormDataState {
  title: string;
  category: string;
  description: string;
  attachment: File | null;
}

interface NewTicketFormProps {
  onTicketSubmit: (ticketData: FormDataState) => void;
}

const NewTicketForm: React.FC<NewTicketFormProps> = ({ onTicketSubmit }) => {
    const [formData, setFormData] = useState({
        title: '',
        category: '',
        description: '',
        attachment: null
    });

    const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
        const { id, value, type } = e.target;
        
        if (type === 'file') {
            const files = (e.target as HTMLInputElement).files;
            setFormData(prev => ({ ...prev, [id]: files ? files[0] : null }));
        } else {
            setFormData(prev => ({ ...prev, [id]: value }));
        }
    };

    const handleSubmit = (e: { preventDefault: () => void; }) => {
        e.preventDefault();
        onTicketSubmit(formData)
        setFormData({ title: '', category: '', description: '', attachment: null });
    };

    return (
        <div className="card card-body mb-4">
            <h5 className="card-title mb-4">Abrir Novo Chamado</h5>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="ticket-title" className="form-label fw-semibold">Título do Chamado</label>
                    <input type="text" className="form-control" id="ticket-title" placeholder="Ex: Não consigo acessar a impressora" value={formData.title} onChange={handleChange} required />
                </div>

                <div className="mb-3">
                    <label htmlFor="ticket-category" className="form-label fw-semibold">Categoria</label>
                    <select className="form-select" id="ticket-category" required value={formData.category} onChange={handleChange}>
                        <option selected disabled value="">Selecione uma categoria...</option>
                        <option value="software">Software</option>
                        <option value="hardware">Hardware</option>
                        <option value="network">Rede e Internet</option>
                        <option value="access">Acessos e Permissões</option>
                        <option value="other">Outro</option>
                    </select>
                </div>

                <div className="mb-3">
                    <label htmlFor="ticket-description" className="form-label fw-semibold">Descrição do Problema</label>
                    <textarea className="form-control" id="ticket-description" rows={4} 
                    placeholder="Detalhe aqui o problema que você está enfrentando..." required value={formData.description} onChange={handleChange}></textarea>
                </div>

                <div className="mb-4">
                    <label htmlFor="ticket-attachment" className="form-label fw-semibold">Anexar Arquivo (Opcional)</label>
                    <input className="form-control" type="file" id="ticket-attachment" onChange={handleChange}/>
                    <div className="form-text">Anexe prints ou documentos que ajudem a descrever o problema.</div>
                </div>

                <button type="submit" className="btn btn-primary">Enviar Chamado</button>
            </form>
        </div>
    )
}

export default NewTicketForm;