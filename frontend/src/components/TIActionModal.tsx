import React, { useState, useEffect, useRef } from 'react';
import { Modal as BootstrapModal } from 'bootstrap';

interface ActionModalProps {
    show: boolean;
    onClose: () => void;
    onSubmit: (text: string) => Promise<void>;
    // isSubmitting: boolean; // REMOVIDO
    title: string;
    label: string;
    submitButtonText: string;
    requiresInput: boolean;
    message?: string;
    placeholder?: string;
}

const ActionModal: React.FC<ActionModalProps> = ({
    show,
    onClose,
    onSubmit,
    // isSubmitting, // REMOVIDO
    title,
    label,
    submitButtonText,
    requiresInput,
    message,
    placeholder
}) => {
    const [inputText, setInputText] = useState('');
    const modalRef = useRef<HTMLDivElement>(null);
    const bsModalInstance = useRef<BootstrapModal | null>(null);

    useEffect(() => {
        if (modalRef.current) {
            if (!bsModalInstance.current) {
                bsModalInstance.current = new BootstrapModal(modalRef.current, {
                    backdrop: 'static',
                    keyboard: false
                });
                modalRef.current.addEventListener('hidden.bs.modal', onClose);
            }

            if (show) {
                bsModalInstance.current.show();
            } else {
                bsModalInstance.current.hide();
            }
        }
        return () => {
            if (modalRef.current && bsModalInstance.current) {
                modalRef.current.removeEventListener('hidden.bs.modal', onClose);
                bsModalInstance.current.dispose();
                bsModalInstance.current = null;
            }
        };
    }, [show, onClose]);

    useEffect(() => {
        if (!show) {
            setInputText('');
        }
    }, [show]);

    const handleSubmit = () => {
        onSubmit(requiresInput ? inputText : ''); 
    };

    return (
        <div className="modal fade" tabIndex={-1} role="dialog" ref={modalRef}> 
            <div className="modal-dialog modal-dialog-centered" role="document">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">{title}</h5>
                        <button type="button" className="btn-close" onClick={onClose}></button> 
                    </div>
                    <div className="modal-body">
                        {requiresInput ? (
                            <div className="mb-3">
                                <label htmlFor="modalInput" className="form-label">{label}</label>
                                <textarea
                                    className="form-control"
                                    id="modalInput"
                                    rows={3}
                                    placeholder={placeholder}
                                    value={inputText}
                                    onChange={(e) => setInputText(e.target.value)}
                                    // disabled={isSubmitting} // REMOVIDO
                                ></textarea>
                            </div>
                        ) : (
                            <p>{message || label}</p>
                        )}
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" onClick={onClose} /* disabled={isSubmitting} */>
                            Fechar
                        </button>
                        <button type="button" className="btn btn-primary" onClick={handleSubmit} disabled={requiresInput && !inputText.trim()}>
                            {/* {isSubmitting ? <span className="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> : null} */}
                            {submitButtonText}
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ActionModal;