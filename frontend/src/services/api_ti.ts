import axios from 'axios'

const api_ti = axios.create({
    baseURL: 'http://localhost:8080',
});

api_ti.interceptors.request.use(
  (config) => {
    const token = sessionStorage.getItem('authToken');

    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

import type { Chamado, AbrirChamadoRequest, ResolverRequest, ReabrirRequest, CancelarRequest, Status } from '../types/chamado.types';

export const abrirChamado = (data: AbrirChamadoRequest) => api_ti.post<Chamado>('/ti/abrir-chamado', data);
export const listarMeusChamados = () => api_ti.get<Chamado[]>('/ti/meus-chamados');
export const cancelarChamado = (id: number, data: CancelarRequest) => api_ti.put(`/ti/${id}/cancelar`, data);

export const listarTodosChamados = () => api_ti.get<Chamado[]>('/ti');
export const listarChamadosPorStatus = (status: Status | 'Todos os Status') => {
    if (status === 'Todos os Status') {
        return api_ti.get<Chamado[]>('/ti');
    }
    return api_ti.get<Chamado[]>(`/ti?status=${status}`);
};

export const buscarChamadoPorId = async (id: number) => {
    try {
        const response = await api_ti.get<Chamado>(`/ti/${id}`);
        return response;
    } catch (error) {
        console.error(`Erro ao obter detalhes do chamado ${id}:`, error);
        throw error;
    }
};

export const iniciarAtendimento = (id: number) => api_ti.put(`/ti/${id}/iniciar-atendimento`);
export const resolverChamado = (id: number, data: ResolverRequest) => api_ti.put(`/ti/${id}/resolver`, data);
export const reabrirChamado = (id: number, data: ReabrirRequest) => api_ti.put(`/ti/${id}/reabrir`, data);
export const fecharChamado = (id: number) => api_ti.put(`/ti/${id}/fechar`);