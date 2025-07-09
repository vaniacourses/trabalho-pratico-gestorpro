import React, { createContext, useState, useEffect, type ReactNode } from 'react';
import { jwtDecode } from 'jwt-decode';
import api from '../services/api';
import { isAxiosError } from 'axios';

interface User {
  email: string;
  roles: string[];
}

interface AuthContextType {
  isAuthenticated: boolean;
  user: User | null;
  isLoading: boolean;
  login: (email: string, pass: string) => Promise<void>;
  logout: () => void;
}

export const AuthContext = createContext<AuthContextType>(null!);

interface AuthProviderProps {
  children: ReactNode;
}

export const AuthProvider = ({ children }: AuthProviderProps) => {
  const [user, setUser] = useState<User | null>(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const loadUserFromToken = async () => {
      const token = sessionStorage.getItem('authToken');

      if (token) {
        try {
          const decodedUser = jwtDecode<User>(token);

          api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
          setUser(decodedUser);
          setIsAuthenticated(true);
        } catch (error) {
          console.error("Sessão inválida, limpando token.", error);
          sessionStorage.removeItem('authToken');
          setUser(null);
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

      const decodedUser = jwtDecode<User>(token);
      setUser(decodedUser);
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
    setUser(null);
    setIsAuthenticated(false);
  };

  const value = {
    isAuthenticated,
    isLoading,
    user,
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