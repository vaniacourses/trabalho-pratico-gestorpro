import { useState } from 'react';
import type { ChangeEvent, FormEvent } from 'react'; 


export interface FormDataState {
  tipoProblema: string;
  urgencia: string;
}

interface NewTicketFormProps {
  onTicketSubmit: (ticketData: FormDataState) => void;
  isSubmitting: boolean;
}

const NewTicketForm: React.FC<NewTicketFormProps> = ({ onTicketSubmit, isSubmitting }) => {
    const [formData, setFormData] = useState<FormDataState>({
        tipoProblema: '',
        urgencia: ''
    });

    const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { id, value } = e.target;
        setFormData(prev => ({ ...prev, [id]: value }));
    };

    const handleSubmit = (e: FormEvent) => {
        e.preventDefault();
        onTicketSubmit(formData);
    };

    return (
        <div className="card card-body mb-4">
            <h5 className="card-title mb-4">Abrir Novo Chamado</h5>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="tipoProblema" className="form-label fw-semibold">Tipo de Problema</label>
                    <input 
                        type="text" 
                        className="form-control" 
                        id="tipoProblema" 
                        placeholder="Ex: Não consigo acessar a impressora" 
                        value={formData.tipoProblema} 
                        onChange={handleChange} 
                        required 
                    />
                </div>

                <div className="mb-3">
                    <label htmlFor="urgencia" className="form-label fw-semibold">Nível de Urgência</label>
                    <select 
                        className="form-select" 
                        id="urgencia" 
                        required 
                        value={formData.urgencia} 
                        onChange={handleChange}
                    >
                        <option value="" disabled>Selecione a urgência...</option>
                        <option value="Baixa">Baixa</option>
                        <option value="Média">Média</option>
                        <option value="Alta">Alta</option>
                    </select>
                </div>

                <button type="submit" className="btn btn-primary" disabled={isSubmitting}>
                    {isSubmitting ? 'Enviando...' : 'Enviar Chamado'}
                </button>
            </form>
        </div>
    );
};

export default NewTicketForm;
