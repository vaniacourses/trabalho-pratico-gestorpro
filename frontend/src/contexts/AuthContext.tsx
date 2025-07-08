import React, { createContext, useState, useEffect, useContext, type ReactNode } from 'react';
import { jwtDecode } from 'jwt-decode';
import api from '../services/api';

// Interface para definir a estrutura do objeto de usuário
interface User {
  email: string;
  roles: string[];
}

// Interface para definir o que o nosso contexto irá fornecer
interface AuthContextType {
  user: User | null;
  isAuthenticated: boolean;
  login: (email: string, pass: string) => Promise<void>;
  logout: () => void;
}

// Criação do Contexto
export const AuthContext = createContext<AuthContextType | undefined>(undefined);

interface AuthProviderProps {
  children: ReactNode;
}

// Componente Provedor que conterá toda a lógica
export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);

  // Efeito que roda uma vez na inicialização para "logar" o usuário
  // automaticamente se um token válido já existir no armazenamento local.
  useEffect(() => {
    const token = localStorage.getItem('accessToken');
    if (token) {
      const decodedToken: { sub: string, roles: string[] } = jwtDecode(token);
      setUser({ email: decodedToken.sub, roles: decodedToken.roles || [] });
    }
  }, []);

  // Função de login que será chamada pelo LoginCard
  const login = async (email: string, password: string) => {
    // Chama o endpoint de login da sua API
    const response = await api.post('/auth/login', { email, password });
    const accessToken = response.data.token;

    // Salva o token no localStorage para persistir a sessão
    localStorage.setItem('accessToken', accessToken);

    // Decodifica o token para obter os dados do usuário
    const decodedToken: { sub: string, roles: string[] } = jwtDecode(accessToken);
    
    // Atualiza o estado global com os dados do usuário
    setUser({ email: decodedToken.sub, roles: decodedToken.roles || [] });
  };

  // Função de logout
  const logout = () => {
    localStorage.removeItem('accessToken');
    setUser(null);
  };

  // O valor fornecido para todos os componentes filhos
  const value = {
    user,
    isAuthenticated: !!user, // Converte o objeto user (ou null) em um booleano
    login,
    logout
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};

// Hook customizado para facilitar o uso do contexto em outros componentes
export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth deve ser usado dentro de um AuthProvider');
  }
  return context;
};