export type TicketStatus = 'Aberto' | 'Em Andamento' | 'Fechado';
export type TicketPriority = 'Alta' | 'Média' | 'Baixa';

export interface Ticket {
  id: number;
  priority: TicketPriority;
  title: string;
  requester: string;
  date: string;
  status: TicketStatus;
}