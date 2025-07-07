package com.gestorpro.gestao_pessoas_service.controller;

import com.gestorpro.gestao_pessoas_service.model.PontoEletronico;
import com.gestorpro.gestao_pessoas_service.repository.PontoEletronicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pontos") // Endpoints para registos de ponto
public class PontoEletronicoController {

    @Autowired
    private PontoEletronicoRepository pontoEletronicoRepository;

    // Endpoint para criar um novo registo de ponto (bater o ponto de entrada)
    @PostMapping
    public ResponseEntity<PontoEletronico> criarRegistroPonto(@RequestBody PontoEletronico novoRegistro) {
        // Exemplo de JSON: { "dataRegistro": "2025-07-06", "horaEntrada": "09:00:00", "funcionario": { "idFuncionario": 1 } }
        // A hora de saída pode ser enviada como nula ou simplesmente omitida.
        PontoEletronico registroSalvo = pontoEletronicoRepository.save(novoRegistro);
        return new ResponseEntity<>(registroSalvo, HttpStatus.CREATED);
    }

    // Endpoint para atualizar um registo de ponto (ex: adicionar a hora de saída)
    @PutMapping("/{idPonto}")
    public ResponseEntity<PontoEletronico> atualizarRegistroPonto(@PathVariable Integer idPonto, @RequestBody PontoEletronico pontoAtualizado) {
        Optional<PontoEletronico> pontoExistenteOpt = pontoEletronicoRepository.findById(idPonto);

        if (pontoExistenteOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 se não encontrar o registo
        }

        PontoEletronico pontoExistente = pontoExistenteOpt.get();
        // Atualiza apenas os campos que podem ser modificados, como a hora de saída.
        if (pontoAtualizado.getHoraSaida() != null) {
            pontoExistente.setHoraSaida(pontoAtualizado.getHoraSaida());
        }

        PontoEletronico registroSalvo = pontoEletronicoRepository.save(pontoExistente);
        return new ResponseEntity<>(registroSalvo, HttpStatus.OK);
    }

    // Endpoint para listar todos os registos de ponto
    @GetMapping
    public ResponseEntity<List<PontoEletronico>> listarTodosRegistros() {
        List<PontoEletronico> todosOsRegistros = pontoEletronicoRepository.findAll();
        return new ResponseEntity<>(todosOsRegistros, HttpStatus.OK);
    }

    // Endpoint para listar todos os registos de um funcionário específico
    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<PontoEletronico>> listarRegistrosPorFuncionario(@PathVariable Integer idFuncionario) {
        List<PontoEletronico> registrosDoFuncionario = pontoEletronicoRepository.findByFuncionario_IdFuncionario(idFuncionario);
        return new ResponseEntity<>(registrosDoFuncionario, HttpStatus.OK);
    }
}
