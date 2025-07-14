import React, { useState, useEffect } from 'react';

interface SimpleConfirmationModalProps {
  show: boolean;
  title: string;
  message?: string;
  label?: string;
  placeholder?: string;
  submitButtonText: string;
  requiresInput: boolean;
  onConfirm: (text: string) => void;
  onCancel: () => void;
  isProcessing: boolean;
}

const SimpleConfirmationModal: React.FC<SimpleConfirmationModalProps> = ({
  show,
  title,
  message,
  label,
  placeholder,
  submitButtonText,
  requiresInput,
  onConfirm,
  onCancel,
  isProcessing,
}) => {
  const [inputText, setInputText] = useState('');

  useEffect(() => {
    if (!show) {
      setInputText('');
    }
  }, [show]);

  if (!show) {
    return null;
  }

  return (
    <div 
      style={{
        position: 'fixed',
        top: 0,
        left: 0,
        width: '100%',
        height: '100%',
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        zIndex: 1050,
      }}
    >
      <div 
        style={{
          backgroundColor: 'white',
          padding: '20px',
          borderRadius: '8px',
          boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
          width: '90%',
          maxWidth: '500px',
          position: 'relative',
        }}
      >
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '15px', borderBottom: '1px solid #eee', paddingBottom: '10px' }}>
          <h5 style={{ margin: 0, fontSize: '1.25rem' }}>{title}</h5>
          <button 
            onClick={onCancel} 
            style={{ 
              background: 'none', 
              border: 'none', 
              fontSize: '1.5rem', 
              cursor: 'pointer', 
              color: '#aaa',
              lineHeight: 1
            }}
            disabled={isProcessing}
          >
            &times;
          </button>
        </div>

        <div style={{ marginBottom: '20px' }}>
          {requiresInput ? (
            <div>
              <label htmlFor="modalInput" style={{ display: 'block', marginBottom: '8px', fontWeight: 'bold' }}>{label}</label>
              <textarea
                id="modalInput"
                rows={3}
                placeholder={placeholder}
                value={inputText}
                onChange={(e) => setInputText(e.target.value)}
                disabled={isProcessing}
                style={{
                  width: '100%',
                  padding: '10px',
                  borderRadius: '4px',
                  border: '1px solid #ccc',
                  resize: 'vertical',
                  fontSize: '1rem',
                }}
              ></textarea>
            </div>
          ) : (
            <p style={{ margin: 0, fontSize: '1rem' }}>{message || label}</p>
          )}
        </div>

        <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '10px' }}>
          <button 
            onClick={onCancel} 
            disabled={isProcessing}
            style={{
              padding: '10px 20px',
              borderRadius: '5px',
              border: '1px solid #ccc',
              backgroundColor: '#f8f9fa',
              cursor: 'pointer',
              fontSize: '1rem'
            }}
          >
            Fechar
          </button>
          <button 
            onClick={() => onConfirm(requiresInput ? inputText : '')}
            disabled={isProcessing || (requiresInput && !inputText.trim())}
            style={{
              padding: '10px 20px',
              borderRadius: '5px',
              border: 'none',
              backgroundColor: '#007bff',
              color: 'white',
              cursor: 'pointer',
              fontSize: '1rem'
            }}
          >
            {isProcessing ? (
              <span 
                style={{ 
                  display: 'inline-block', 
                  width: '1em', 
                  height: '1em', 
                  border: '0.15em solid currentColor', 
                  borderRightColor: 'transparent', 
                  borderRadius: '50%', 
                  animation: 'spinner-border .75s linear infinite', 
                  verticalAlign: '-0.125em' 
                }}
              ></span>
            ) : null}
            {submitButtonText}
          </button>
        </div>
      </div>
    </div>
  );
};

export default SimpleConfirmationModal;