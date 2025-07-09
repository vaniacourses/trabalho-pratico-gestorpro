import React, { createContext, useState, useEffect, type ReactNode } from 'react';
import api from '../services/api';
import { isAxiosError } from 'axios';

interface AuthContextType {
  isAuthenticated: boolean;
  isLoading: boolean;
  login: (email: string, pass: string) => Promise<void>;
  logout: () => void;
}

export const AuthContext = createContext<AuthContextType>(null!);

interface AuthProviderProps {
  children: ReactNode;
}

export const AuthProvider = ({ children }: AuthProviderProps) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const loadUserFromToken = async () => {
      const token = sessionStorage.getItem('authToken');

      if (token) {
        try {
          api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
          setIsAuthenticated(true);
          
        } catch (error) {
          console.error("Sessão inválida, limpando token.", error);
          sessionStorage.removeItem('authToken');
          setIsAuthenticated(false);
        }
      }
      setIsLoading(false);
    };

    loadUserFromToken();
  }, []);

  const login = async (email: string, pass: string) => {
    try {
      const response = await api.post('/auth/login', {
        email: email,
        password: pass,
      });

      const { token } = response.data; 
      console.log(token);
      sessionStorage.setItem('authToken', token);
      
      api.defaults.headers.common['Authorization'] = `Bearer ${token}`;

      setIsAuthenticated(true);

      console.log("Login bem-sucedido!");

    } catch (error) {
      setIsAuthenticated(false);
      if (isAxiosError(error)) {
        if (error.response) {
          console.error('Erro de resposta da API:', error.response.data);
        } else {
          console.error('Erro de rede:', error.message);
        }
      } else {
        console.error('Erro inesperado:', error);
      }
      throw error;
    }
  };

  const logout = () => {
    sessionStorage.removeItem('authToken');
    delete api.defaults.headers.common['Authorization'];
  };

  const value = {
    isAuthenticated,
    isLoading,
    login,
    logout,
  };
  
  if (isLoading) {
    return <div>Carregando aplicação...</div>;
  }

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};