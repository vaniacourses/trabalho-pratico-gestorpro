export interface Employee {
  id: number;
  nome: string;
  cargo: string;
  dataAdmissao: string; // Formato YYYY-MM-DD para facilitar a manipulação
  status: 'Ativo' | 'Inativo'; // Usando um tipo literal para segurança
}