import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';

const LoginCard: React.FC = () => {
    const navigate = useNavigate();
    const { login } = useAuth();

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        setIsLoading(true);
        setError(null);

        try {
            await login(email, password);
            navigate('/');
        } catch (err) {
            setError('Falha ao fazer login. Verifique suas credenciais.');
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="login-card">
            <div className="logo-container">
                <div className="logo-hexagon"></div>
                <h1>GestorPro</h1>
            </div>

            <div className="card-header">
                <h2>Bem-vindo de volta!</h2>
                <p>Acesse sua conta para continuar.</p>
            </div>

            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="email">E-mail</label>
                    <input type="email" id="email" placeholder="seuemail@empresa.com" required value={email} onChange={(evento) => setEmail(evento.target.value)}/>
                </div>
                <div className="form-group">
                    <label htmlFor="password">Senha</label>
                    <input type="password" id="password" placeholder="Sua senha" required
                    value={password} onChange={(evento) => setPassword(evento.target.value)}/>
                </div>

                {error && <div className="error-message">{error}</div>}

                <button type="submit" className="login-button" disabled={isLoading}>Entrar</button>
            </form>
        </div>
    )
}

export default LoginCard;