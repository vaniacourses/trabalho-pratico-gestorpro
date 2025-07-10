export type Status = 'ABERTO' | 'EM_ANDAMENTO' | 'RESOLVIDO' | 'CANCELADO' | 'FECHADO';

export const ALL_STATUSES: Status[] = [
    'ABERTO',
    'EM_ANDAMENTO',
    'RESOLVIDO',
    'CANCELADO',
    'FECHADO',
]

export interface Chamado {
    id: number;
    tipoProblema: string;
    urgencia: string;
    solicitanteEmail: string;
    status: Status;
    dataHoraCriacao: string;
}

/**
 * DTO para a requisição de ABRIR um novo chamado.
 */
export interface AbrirChamadoRequest {
    tipoProblema: string;
    urgencia: string;
}

/**
 * DTO para a requisição de RESOLVER um chamado.
 */
export interface ResolverRequest {
    solucao: string;
}

/**
 * DTO para a requisição de REABRIR um chamado.
 */
export interface ReabrirRequest {
    motivo: string;
}

/**
 * DTO para a requisição de CANCELAR um chamado.
 */
export interface CancelarRequest {
    motivo: string;
}