import api from './api';
import type { Chamado, AbrirChamadoRequest, ResolverRequest, ReabrirRequest, CancelarRequest, Status } from '../types/chamado.types';

// Ações para qualquer usuário
export const abrirChamado = (data: AbrirChamadoRequest) => api.post<Chamado>('/ti/abrir-chamado', data);
export const listarMeusChamados = () => api.get<Chamado[]>('/ti/meus-chamados');
export const cancelarChamado = (id: number, data: CancelarRequest) => api.put(`/ti/${id}/cancelar`, data);

// Ações exclusivas da TI
export const listarTodosChamados = () => api.get<Chamado[]>('/ti');
export const listarChamadosPorStatus = (status: Status) => api.get<Chamado[]>(`/ti?status=${status}`);
export const buscarChamadoPorId = (id: number) => api.get<Chamado>(`/ti/${id}`);
export const iniciarAtendimento = (id: number) => api.put(`/ti/${id}/iniciar-atendimento`);
export const resolverChamado = (id: number, data: ResolverRequest) => api.put(`/ti/${id}/resolver`, data);
export const reabrirChamado = (id: number, data: ReabrirRequest) => api.put(`/ti/${id}/reabrir`, data);
export const fecharChamado = (id: number) => api.put(`/ti/${id}/fechar`);