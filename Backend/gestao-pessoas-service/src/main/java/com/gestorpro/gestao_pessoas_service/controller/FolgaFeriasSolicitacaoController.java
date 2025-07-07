package com.gestorpro.gestao_pessoas_service.controller;

import com.gestorpro.gestao_pessoas_service.model.FolgaFeriasSolicitacao;
import com.gestorpro.gestao_pessoas_service.repository.FolgaFeriasSolicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/solicitacoes") // Endpoints para solicitações de folga/férias
public class FolgaFeriasSolicitacaoController {

    @Autowired
    private FolgaFeriasSolicitacaoRepository solicitacaoRepository;

    // Endpoint para criar uma nova solicitação
    @PostMapping
    public ResponseEntity<FolgaFeriasSolicitacao> criarSolicitacao(@RequestBody FolgaFeriasSolicitacao novaSolicitacao) {
        // Exemplo de JSON: { "tipo": "Férias", "dataInicio": "2025-10-01", "dataFim": "2025-10-15", "funcionario": { "idFuncionario": 1 } }
        // O status será "Pendente" por padrão.
        FolgaFeriasSolicitacao solicitacaoSalva = solicitacaoRepository.save(novaSolicitacao);
        return new ResponseEntity<>(solicitacaoSalva, HttpStatus.CREATED);
    }

    // Endpoint para listar todas as solicitações
    @GetMapping
    public ResponseEntity<List<FolgaFeriasSolicitacao>> listarTodasSolicitacoes() {
        return new ResponseEntity<>(solicitacaoRepository.findAll(), HttpStatus.OK);
    }
    
    // Endpoint para APROVAR ou REJEITAR uma solicitação
    @PatchMapping("/{idSolicitacao}/status")
    public ResponseEntity<FolgaFeriasSolicitacao> atualizarStatus(
            @PathVariable Integer idSolicitacao,
            @RequestBody Map<String, String> statusUpdate) {

        Optional<FolgaFeriasSolicitacao> solicitacaoOpt = solicitacaoRepository.findById(idSolicitacao);

        if (solicitacaoOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        FolgaFeriasSolicitacao solicitacao = solicitacaoOpt.get();
        String novoStatus = statusUpdate.get("status");

        // Valida se o status enviado é um dos valores permitidos
        if (novoStatus != null && (novoStatus.equalsIgnoreCase("Aprovada") || novoStatus.equalsIgnoreCase("Rejeitada"))) {
            // Atualiza o status do enum
            solicitacao.setStatus(novoStatus.equalsIgnoreCase("Aprovada")
                    ? com.gestorpro.gestao_pessoas_service.model.StatusSolicitacao.Aprovada
                    : com.gestorpro.gestao_pessoas_service.model.StatusSolicitacao.Rejeitada);
            
            FolgaFeriasSolicitacao solicitacaoAtualizada = solicitacaoRepository.save(solicitacao);
            return new ResponseEntity<>(solicitacaoAtualizada, HttpStatus.OK);
        } else {
            // Retorna um erro se o status for inválido
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
