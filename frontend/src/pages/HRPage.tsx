import React, {useState} from 'react';
import GenericForm from '../components/GenericForm';
import type { FormField } from '../components/GenericForm';
import Navbar from '../components/Navbar';
import "../components/global.css"

const HRPage: React.FC = () => {
    const [isFormVisible, setIsFormVisible] = useState(false);
    const employeeFormFields: FormField[] = [
        {
        id: 'nome',
        label: 'Nome Completo',
        type: 'text',
        placeholder: 'Digite o nome completo do funcionário',
        required: true,
        },
        {
        id: 'cargo',
        label: 'Cargo',
        type: 'text',
        placeholder: 'Ex: Desenvolvedor(a) Frontend Pleno',
        required: true,
        },
        {
        id: 'departamento',
        label: 'Departamento',
        type: 'text',
        placeholder: 'Ex: Tecnologia',
        required: true,
        },
        {
        id: 'telefone',
        label: 'Telefone',
        type: 'text',
        placeholder: '(XX) XXXXX-XXXX',
        },
        {
        id: 'email',
        label: 'Email Corporativo',
        type: 'email',
        placeholder: 'nome.sobrenome@empresa.com',
        required: true,
        },
        {
        id: 'senha',
        label: 'Senha Provisória',
        type: 'password',
        required: true,
        helpText: 'O funcionário deverá alterar esta senha no primeiro login.',
        },
        {
        id: 'tipoContrato',
        label: 'Tipo de Contrato',
        type: 'select',
        required: true,
        options: [
            { value: 'CLT', label: 'CLT' },
            { value: 'PJ', label: 'Pessoa Jurídica (PJ)' },
            { value: 'Estagio', label: 'Estágio' },
            { value: 'Temporario', label: 'Temporário' },
        ],
        },
        {
            id: 'jornadaDeTrabalho',
            label: 'Jornada de Trabalho',
            type: 'number',
            placeholder: 'Ex: 44',
            required: true
        },
        {
            id: 'salarioInicial',
            label: 'Salário Inicial (R$)',
            type: 'number',
            placeholder: 'Ex: 5000',
            required: true
        }
    ];

    const toggleFormVisibility = () => {
        setIsFormVisible(currentValue => !currentValue);
    };

  const handleCreateEmployee = (formData: Record<string, any>) => {
    console.log('Dados do novo funcionário para criação:', formData);
    // Em uma aplicação real, aqui você faria uma chamada para a sua API:
    // try {
    //   await api.post('/employees', formData);
    //   alert(`Funcionário ${formData.nome} criado com sucesso!`);
    // } catch (error) {
    //   alert('Ocorreu um erro ao criar o funcionário.');
    // }
    setIsFormVisible(false);
  };

  return (
    <>
        <Navbar/>
        <main className="container mt-4 mb-5">
            
            <header className="mb-4">
                <h1 className="fw-bolder">Recursos Humanos</h1>
                <p className="text-secondary fs-5">Gerencie o recurso mais valioso do nosso grupo.</p>
            </header>

            <div className="d-grid gap-2 d-md-flex justify-content-md mb-4">
                <button className="btn btn-primary fw-semibold" type="button" onClick={toggleFormVisibility}>
                    <i className="bi bi-plus-circle me-1"></i>
                    {isFormVisible ? 'Fechar formulário' : 'Adicionar funcionário'}
                </button>
            </div>
            <div className={`collapse ${isFormVisible ? 'show' : ''}`}>
                <GenericForm
                    title="Adicionar funcionário"
                    fields={employeeFormFields}
                    submitButtonText="Criar funcionário"
                    onSubmit={handleCreateEmployee}
                />
            </div>
        </main>
    </>
  );
};

export default HRPage;