[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg)](https://classroom.github.com/online_ide?assignment_repo_id=19566675&assignment_repo_type=AssignmentRepo)

Documentação do sistema proposto: https://docs.google.com/document/d/1kZacFjZhgNes6niiBnmMo2jFiKZ8HnAlJRLoQagE6D8/edit?usp=sharing

# Trabalho para a Disciplina de Projeto de Software (Entrega 1)

## Grupo:
* Bruno Porto
* Igor Rodrigues
* Pedro Moreira
* Ricardo Araújo
* Yan Novaes

---

## Atribuição das Responsabilidades Iniciais:

A seguir, a distribuição das responsabilidades para a **Entrega 1** do projeto:

1.  **Elaboração do Escopo do Projeto**: Decisão conjunta dos membros do grupo.
2.  **Elicitação de Requisitos (Funcionais, Não-Funcionais e Arquiteturais)**: Igor Rodrigues e Yan Novaes.
3.  **Decisões e Restrições Arquiteturais**: Yan Novaes.
4.  **Definição e Justificativa do Estilo Arquitetural**: Ricardo Araújo e Yan Novaes.
5.  **Definição e Diagramação dos Casos de Uso**: Ricardo Araújo e Pedro Moreira.
6.  **Diagrama Conceitual de Classes**: Pedro Moreira e Bruno Porto.
7.  **Diagramas de Sequência**: Cada integrante ficou responsável por um caso de uso para diagramar.
8.  **Diagrama de Classes Detalhado**: Igor Rodrigues.

---

## Alterações Realizadas Após a Primeira Apresentação:

Após a primeira apresentação, as seguintes alterações e novas atribuições foram implementadas:

1.  **Diagramas de Sequência de Sistema**: Cada integrante criou o Diagrama de Sequência de Sistema para um dos Diagramas de Sequência feitos anteriormente.
2.  **Diagramas de Classes por Módulo (Microsserviços)**: O Diagrama de Classes detalhado foi desmembrado em diagramas menores, atendendo às exigências do estilo arquitetural de microsserviços. Cada diagrama representa um dos módulos independentes do sistema, com a seguinte atribuição:
    * **Diagrama de Classes do Módulo Administrativo**: Igor Rodrigues
    * **Diagrama de Classes do Módulo de TI**: Yan Novaes
    * **Diagrama de Classes do Módulo Financeiro**: Ricardo Araújo
    * **Diagrama de Classes do Módulo de RH**: Bruno Porto
    * **Diagrama de Classes do Módulo de Gestão de Projetos**: Pedro Moreira

## Implementação dos Padrões GoF

### Padrão State
- Local: `ti-service/.../model/state/`

### Padrão Strategy
- Local: `financeiro-service/.../external/notificacao/`

### Padrão Builder
- Local: `gestao-pessoas-service/.../service/builder/`