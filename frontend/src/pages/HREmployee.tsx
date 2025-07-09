import React, { useState } from 'react';

const HREmployee: React.FC = () => {
  const [clockedIn, setClockedIn] = useState<boolean>(false);
  const [lastClockEvent, setLastClockEvent] = useState<string | null>(null);

  const handleClock = () => {
    const now = new Date();
    const eventType = clockedIn ? 'Saída' : 'Entrada';
    
    setLastClockEvent(`${eventType} registrada às: ${now.toLocaleTimeString('pt-BR')}`);
    setClockedIn(!clockedIn);
    alert(`${eventType} registrada com sucesso!`);
  };

  return (
    <main className="container">
       <div>
          <h1>Portal do Funcionário</h1>
          <p>Bem-vindo(a)! Gerencie seu ponto e benefícios aqui.</p>
        </div>

        <div className="grid-container">
            <div className="content-card">
                <h2>Registro de Ponto</h2>
                <p>Use o botão abaixo para registrar sua entrada e saída.</p>
                <button className={`btn ${clockedIn ? 'btn-danger' : 'btn-primary'}`} onClick={handleClock}>
                    {clockedIn ? 'Registrar Saída' : 'Registrar Entrada'}
                </button>
                {lastClockEvent && <p className="feedback-text">{lastClockEvent}</p>}
            </div>

            <div className="content-card">
                <h2>Benefícios</h2>
                <p>Precisa de um novo benefício ou quer alterar um existente?</p>
                <button className="btn btn-secondary" onClick={() => alert('Abrir formulário de solicitação de benefício.')}>
                    Solicitar Benefício
                </button>
            </div>
        </div>
    </main>
  );
}

export default HREmployee;