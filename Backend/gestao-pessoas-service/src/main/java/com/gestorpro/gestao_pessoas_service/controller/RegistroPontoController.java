package com.gestorpro.gestao_pessoas_service.controller;

import com.gestorpro.gestao_pessoas_service.dto.RegistroPontoDto;
import com.gestorpro.gestao_pessoas_service.model.RegistroPonto;
import com.gestorpro.gestao_pessoas_service.service.ServicoDePonto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rh/ponto")
public class RegistroPontoController {

    @Autowired
    private ServicoDePonto servicoDePonto;

    /**
     * Endpoint para um funcionário registrar a entrada do dia.
     * Recebe um JSON com o ID do funcionário.
     * @param dto O objeto com os dados da requisição.
     * @return O registro de ponto criado.
     */
    @PostMapping("/entrada")
    public ResponseEntity<RegistroPonto> registrarEntrada(@Valid @RequestBody RegistroPontoDto dto) {
        RegistroPonto registro = servicoDePonto.registrarEntrada(dto.getIdFuncionario());
        return ResponseEntity.ok(registro);
    }

    /**
     * Endpoint para um funcionário registrar a saída do dia.
     * Recebe um JSON com o ID do funcionário.
     * @param dto O objeto com os dados da requisição.
     * @return O registro de ponto atualizado com a hora de saída.
     */
    @PostMapping("/saida")
    public ResponseEntity<RegistroPonto> registrarSaida(@Valid @RequestBody RegistroPontoDto dto) {
        RegistroPonto registro = servicoDePonto.registrarSaida(dto.getIdFuncionario());
        return ResponseEntity.ok(registro);
    }

    /**
     * Endpoint para um gestor validar um registro de ponto.
     * @param idRegistro O ID do registro de ponto a ser validado.
     * @return O registro de ponto com o status atualizado para "VALIDADO".
     */
    @PutMapping("/{id}/validar")
    public ResponseEntity<RegistroPonto> validarPonto(@PathVariable("id") Integer idRegistro) {
        RegistroPonto registro = servicoDePonto.validar(idRegistro);
        return ResponseEntity.ok(registro);
    }

    /**
     * Endpoint para listar todos os registros de ponto de um funcionário.
     * @param idFuncionario O ID do funcionário.
     * @return Uma lista com o histórico de pontos do funcionário.
     */
    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<RegistroPonto>> listarPorFuncionario(@PathVariable Integer idFuncionario) {
        List<RegistroPonto> registros = servicoDePonto.listarPorFuncionario(idFuncionario);
        return ResponseEntity.ok(registros);
    }
}