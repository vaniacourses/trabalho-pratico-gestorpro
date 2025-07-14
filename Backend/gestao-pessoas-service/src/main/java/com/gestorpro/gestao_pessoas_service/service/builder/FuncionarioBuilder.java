package com.gestorpro.gestao_pessoas_service.service.builder;

import com.gestorpro.gestao_pessoas_service.dto.CreateUserDto;
import com.gestorpro.gestao_pessoas_service.model.*;
import com.gestorpro.gestao_pessoas_service.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private String role;
    private String tipoContrato;
    private Integer jornada;
    private User user;
    private BigDecimal salarioInicial;

    // --- Dependências necessárias para a construção ---
    private final FuncionarioRepository funcionarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final ContratoRepository contratoRepository;
    private final SalarioRepository salarioRepository;
    private final RoleRepository roleRepository;

    // O construtor recebe os repositórios do serviço que o criou.
    public FuncionarioBuilder(FuncionarioRepository fRepo, UsuarioRepository uRepo, ContratoRepository cRepo, SalarioRepository sRepo, RoleRepository rRepo) {
        this.funcionarioRepository = fRepo;
        this.usuarioRepository = uRepo;
        this.contratoRepository = cRepo;
        this.salarioRepository = sRepo;
        this.roleRepository = rRepo;  
    }

    // --- Métodos fluentes para configurar o objeto ---
    public FuncionarioBuilder comDadosPessoais(String nome, String cargo, String depto, String tel) {
        this.nome = nome;
        this.cargo = cargo;
        this.departamento = depto;
        this.telefone = tel;
        return this; // Retorna a própria instância para encadear chamadas
    }

    public FuncionarioBuilder comCredenciais(String email, String senha, String role) {
        this.email = email;
        this.senha = senha;
        this.role = role;
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

        List<Role> rolesDoUsuario = new ArrayList<>();
        Role role = roleRepository.findByName(RoleName.valueOf(this.role)).orElseThrow(() -> new RuntimeException("Role não encontrada: " + this.role));;
        rolesDoUsuario.add(role);
        // Cria e salva o Usuario
        User novoUsuario = new User();
        novoUsuario.setEmail(this.email);
        novoUsuario.setPassword(this.senha);
        novoUsuario.setRoles(rolesDoUsuario);

        // Cria e salva o Funcionario, associando o Usuario
        Funcionario novoFuncionario = new Funcionario();
        novoFuncionario.setNome(this.nome);
        novoFuncionario.setCargo(this.cargo);
        novoFuncionario.setDepartamento(this.departamento);
        novoFuncionario.setTelefone(this.telefone);
        novoFuncionario.setData_admissao(LocalDate.now());
        novoFuncionario.setUsuario(novoUsuario);
        funcionarioRepository.save(novoFuncionario);

        // Cria e salva o Contrato inicial
        Contrato novoContrato = new Contrato();
        novoContrato.setTipo(this.tipoContrato);
        novoContrato.setJornada(this.jornada);
        novoContrato.setFuncionario(novoFuncionario);
        contratoRepository.save(novoContrato);

        // Cria e salva o Salário inicial (se fornecido)
        if (this.salarioInicial != null) {
            Salario novoSalario = new Salario();
            novoSalario.setValor(this.salarioInicial);
            novoSalario.setDataPagamento(LocalDate.now());
            novoSalario.setFuncionario(novoFuncionario);
            salarioRepository.save(novoSalario);
        }

        return novoFuncionario;
    }

    public FuncionarioBuilder setUsuario(User user2) {
        this.user = user2;
        return this;
    }
}