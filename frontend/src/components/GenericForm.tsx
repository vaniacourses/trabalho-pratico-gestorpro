import React, { useState } from 'react';
import type { ChangeEvent, FormEvent } from 'react';

export interface FormField {
  id: string;
  label: string;
  type: 'text' | 'select' | 'textarea' | 'file' | 'email' | 'password' | 'number' | 'date';
  placeholder?: string;
  required?: boolean;
  options?: { value: string; label: string }[];
  rows?: number;
  helpText?: string; 
}

interface GenericFormProps {
  title: string;
  fields: FormField[];
  submitButtonText: string;
  onSubmit: (formData: Record<string, any>) => void;
}

const GenericForm: React.FC<GenericFormProps> = ({ title, fields, submitButtonText, onSubmit }) => {
  const createInitialState = () => {
    return fields.reduce((acc, field) => {
      acc[field.id] = field.type === 'file' ? null : '';
      return acc;
    }, {} as Record<string, any>);
  };

  const [formData, setFormData] = useState(createInitialState());

  const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
    const { id, value, type } = e.target;
    
    if (type === 'file') {
      const files = (e.target as HTMLInputElement).files;
      setFormData(prev => ({ ...prev, [id]: files ? files[0] : null }));
    } else {
      setFormData(prev => ({ ...prev, [id]: value }));
    }
  };

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    onSubmit(formData);
    setFormData(createInitialState()); 
  };


  const renderField = (field: FormField) => {
    const commonProps = {
      id: field.id,
      className: field.type === 'file' ? 'form-control' : 'form-select',
      onChange: handleChange,
      required: field.required,
    };

    switch (field.type) {
      case 'select':
        return (
          <select {...commonProps} value={formData[field.id]}>
            <option disabled value="">Selecione uma opção...</option>
            {field.options?.map(opt => (
              <option key={opt.value} value={opt.value}>{opt.label}</option>
            ))}
          </select>
        );
      case 'textarea':
        return (
          <textarea
            id={field.id}
            className="form-control"
            rows={field.rows || 4}
            placeholder={field.placeholder}
            value={formData[field.id]}
            onChange={handleChange}
            required={field.required}
          />
        );
      default:
        return (
          <input
            type={field.type}
            id={field.id}
            className="form-control"
            placeholder={field.placeholder}
            value={field.type !== 'file' ? formData[field.id] : undefined}
            onChange={handleChange}
            required={field.required}
          />
        );
    }
  };

  return (
    <div className="card card-body mb-4">
      <h5 className="card-title mb-4">{title}</h5>
      <form onSubmit={handleSubmit}>
        {fields.map(field => (
          <div className="mb-3" key={field.id}>
            <label htmlFor={field.id} className="form-label fw-semibold">{field.label}</label>
            {renderField(field)}
            {field.helpText && <div className="form-text">{field.helpText}</div>}
          </div>
        ))}
        <button type="submit" className="btn btn-primary mt-3">{submitButtonText}</button>
      </form>
    </div>
  );
};

export default GenericForm;