package com.gestorpro.gestao_pessoas_service.service.builder;

import com.gestorpro.gestao_pessoas_service.model.*;
import com.gestorpro.gestao_pessoas_service.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

// Esta é a classe Builder. Ela não é um @Service ou @Component,
// pois é instanciada diretamente pelo ServicoFuncionario.
public class FuncionarioBuilder {

    // --- Atributos para guardar as peças da construção ---
    private String nome;
    private String cargo;
    private String departamento;
    private String telefone;
    private String email;
    private String senha;
    private String tipoContrato;
    private Integer jornada;
    private BigDecimal salarioInicial;

    // --- Dependências necessárias para a construção ---
    private final FuncionarioRepository funcionarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final ContratoRepository contratoRepository;
    private final SalarioRepository salarioRepository;

    // O construtor recebe os repositórios do serviço que o criou.
    public FuncionarioBuilder(FuncionarioRepository fRepo, UsuarioRepository uRepo, ContratoRepository cRepo, SalarioRepository sRepo) {
        this.funcionarioRepository = fRepo;
        this.usuarioRepository = uRepo;
        this.contratoRepository = cRepo;
        this.salarioRepository = sRepo;
    }

    // --- Métodos fluentes para configurar o objeto ---
    public FuncionarioBuilder comDadosPessoais(String nome, String cargo, String depto, String tel) {
        this.nome = nome;
        this.cargo = cargo;
        this.departamento = depto;
        this.telefone = tel;
        return this; // Retorna a própria instância para encadear chamadas
    }

    public FuncionarioBuilder comCredenciais(String email, String senha) {
        this.email = email;
        this.senha = senha;
        return this;
    }

    public FuncionarioBuilder comContratoInicial(String tipo, Integer jornada) {
        this.tipoContrato = tipo;
        this.jornada = jornada;
        return this;
    }
    
    public FuncionarioBuilder comSalarioInicial(BigDecimal salario) {
        this.salarioInicial = salario;
        return this;
    }


    // --- O método final que executa a construção ---
    public Funcionario build() {
        if (nome == null || email == null || senha == null) {
            throw new IllegalStateException("Dados pessoais e credenciais são obrigatórios para a contratação.");
        }

        // 1. Cria e salva o Usuario
        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(this.email);
        novoUsuario.setSenha(this.senha); // ATENÇÃO: Em um projeto real, a senha deve ser criptografada aqui!
        usuarioRepository.save(novoUsuario);

        // 2. Cria e salva o Funcionario, associando o Usuario
        Funcionario novoFuncionario = new Funcionario();
        novoFuncionario.setNome(this.nome);
        novoFuncionario.setCargo(this.cargo);
        novoFuncionario.setDepartamento(this.departamento);
        novoFuncionario.setTelefone(this.telefone);
        novoFuncionario.setData_admissao(LocalDate.now());
        novoFuncionario.setUsuario(novoUsuario);
        funcionarioRepository.save(novoFuncionario);

        // 3. Cria e salva o Contrato inicial
        Contrato novoContrato = new Contrato();
        novoContrato.setTipo(this.tipoContrato);
        novoContrato.setJornada(this.jornada);
        novoContrato.setFuncionario(novoFuncionario);
        contratoRepository.save(novoContrato);

        // 4. Cria e salva o Salário inicial (se fornecido)
        if (this.salarioInicial != null) {
            Salario novoSalario = new Salario();
            novoSalario.setValor(this.salarioInicial);
            novoSalario.setDataPagamento(LocalDate.now());
            novoSalario.setFuncionario(novoFuncionario);
            salarioRepository.save(novoSalario);
        }

        return novoFuncionario;
    }
}