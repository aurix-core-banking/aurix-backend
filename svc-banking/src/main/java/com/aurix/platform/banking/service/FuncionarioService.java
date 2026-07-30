package com.aurix.platform.banking.service;

import com.aurix.platform.banking.dto.FuncionarioDTO;
import com.aurix.platform.banking.entity.Funcionario;
import com.aurix.platform.banking.entity.Empresa;
import com.aurix.platform.banking.entity.Departamento;
import com.aurix.platform.banking.entity.Cargo;
import com.aurix.platform.banking.repository.FuncionarioRepository;
import com.aurix.platform.banking.repository.EmpresaRepository;
import com.aurix.platform.banking.repository.DepartamentoRepository;
import com.aurix.platform.banking.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private CargoRepository cargoRepository;

    public List<Funcionario> listarTodosFuncionarios() {
        return funcionarioRepository.findAll();
    }

    public Funcionario buscarFuncionarioPorId(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));
    }

    public Funcionario buscarFuncionarioPorMatricula(String matricula) {
        return funcionarioRepository.findByMatricula(matricula)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com matrícula: " + matricula));
    }

    public List<Funcionario> buscarFuncionariosPorEmpresa(Long empresaId) {
        return funcionarioRepository.findFuncionariosAtivosByEmpresa(empresaId);
    }

    public List<Funcionario> buscarFuncionariosPorDepartamento(Long departamentoId) {
        return funcionarioRepository.findFuncionariosAtivosByDepartamento(departamentoId);
    }

    public List<Funcionario> buscarFuncionariosPorCargo(Long cargoId) {
        return funcionarioRepository.findFuncionariosAtivosByCargo(cargoId);
    }

    public List<Funcionario> buscarSubordinados(Long gestorId) {
        return funcionarioRepository.findSubordinadosAtivos(gestorId);
    }

    public List<Funcionario> buscarFuncionarios(String nome, Long empresaId, Long departamentoId, String status) {
        if (nome != null && !nome.isEmpty()) {
            return funcionarioRepository.findByNomeContainingIgnoreCase(nome);
        }
        if (empresaId != null) {
            return funcionarioRepository.findByEmpresaId(empresaId);
        }
        if (departamentoId != null) {
            return funcionarioRepository.findByDepartamentoId(departamentoId);
        }
        if (status != null) {
            Funcionario.StatusFuncionario statusEnum = Funcionario.StatusFuncionario
                    .valueOf(status.toUpperCase(java.util.Locale.ROOT));
            return funcionarioRepository.findByStatus(statusEnum);
        }
        return funcionarioRepository.findAll();
    }

    public Funcionario criarFuncionario(FuncionarioDTO funcionarioDTO) {
        if (funcionarioRepository.existsByMatricula(funcionarioDTO.getMatricula())) {
            throw new RuntimeException("Já existe um funcionário com a matrícula: " + funcionarioDTO.getMatricula());
        }

        if (funcionarioDTO.getCpf() != null && funcionarioRepository.existsByCpf(funcionarioDTO.getCpf())) {
            throw new RuntimeException("Já existe um funcionário com o CPF: " + funcionarioDTO.getCpf());
        }

        if (funcionarioDTO.getEmail() != null && funcionarioRepository.existsByEmail(funcionarioDTO.getEmail())) {
            throw new RuntimeException("Já existe um funcionário com o email: " + funcionarioDTO.getEmail());
        }

        Empresa empresa = empresaRepository.findById(funcionarioDTO.getEmpresaId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        Departamento departamento = null;
        if (funcionarioDTO.getDepartamentoId() != null) {
            departamento = departamentoRepository.findById(funcionarioDTO.getDepartamentoId())
                    .orElseThrow(() -> new RuntimeException("Departamento não encontrado"));
        }

        Cargo cargo = null;
        if (funcionarioDTO.getCargoId() != null) {
            cargo = cargoRepository.findById(funcionarioDTO.getCargoId())
                    .orElseThrow(() -> new RuntimeException("Cargo não encontrado"));
        }

        Funcionario gestor = null;
        if (funcionarioDTO.getGestorId() != null) {
            gestor = funcionarioRepository.findById(funcionarioDTO.getGestorId())
                    .orElseThrow(() -> new RuntimeException("Gestor não encontrado"));
        }

        Funcionario funcionario = new Funcionario();
        funcionario.setMatricula(funcionarioDTO.getMatricula());
        funcionario.setNomeCompleto(funcionarioDTO.getNomeCompleto());
        funcionario.setCpf(funcionarioDTO.getCpf());
        funcionario.setEmail(funcionarioDTO.getEmail());
        funcionario.setTelefone(funcionarioDTO.getTelefone());
        funcionario.setEmpresa(empresa);
        funcionario.setDepartamento(departamento);
        funcionario.setCargo(cargo);
        funcionario.setGestor(gestor);
        funcionario.setStatus(
                funcionarioDTO.getStatus() != null ? funcionarioDTO.getStatus() : Funcionario.StatusFuncionario.ATIVO);
        funcionario.setDataAdmissao(
                funcionarioDTO.getDataAdmissao() != null ? funcionarioDTO.getDataAdmissao() : LocalDate.now());
        funcionario.setSalarioAtual(funcionarioDTO.getSalarioAtual());
        funcionario.setDadosFuncionario(funcionarioDTO.getDadosFuncionario());
        funcionario.setPermissoesFuncionario(funcionarioDTO.getPermissoesFuncionario());
        funcionario.setDataCriacao(LocalDateTime.now());
        funcionario.setDataAtualizacao(LocalDateTime.now());

        return funcionarioRepository.save(funcionario);
    }

    public Funcionario atualizarFuncionario(Long id, FuncionarioDTO funcionarioDTO) {
        Funcionario funcionario = buscarFuncionarioPorId(id);

        if (!funcionario.getMatricula().equals(funcionarioDTO.getMatricula()) &&
                funcionarioRepository.existsByMatricula(funcionarioDTO.getMatricula())) {
            throw new RuntimeException("Já existe um funcionário com a matrícula: " + funcionarioDTO.getMatricula());
        }

        if (funcionarioDTO.getCpf() != null && !funcionario.getCpf().equals(funcionarioDTO.getCpf()) &&
                funcionarioRepository.existsByCpf(funcionarioDTO.getCpf())) {
            throw new RuntimeException("Já existe um funcionário com o CPF: " + funcionarioDTO.getCpf());
        }

        if (funcionarioDTO.getEmail() != null && !funcionario.getEmail().equals(funcionarioDTO.getEmail()) &&
                funcionarioRepository.existsByEmail(funcionarioDTO.getEmail())) {
            throw new RuntimeException("Já existe um funcionário com o email: " + funcionarioDTO.getEmail());
        }

        Empresa empresa = empresaRepository.findById(funcionarioDTO.getEmpresaId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        Departamento departamento = null;
        if (funcionarioDTO.getDepartamentoId() != null) {
            departamento = departamentoRepository.findById(funcionarioDTO.getDepartamentoId())
                    .orElseThrow(() -> new RuntimeException("Departamento não encontrado"));
        }

        Cargo cargo = null;
        if (funcionarioDTO.getCargoId() != null) {
            cargo = cargoRepository.findById(funcionarioDTO.getCargoId())
                    .orElseThrow(() -> new RuntimeException("Cargo não encontrado"));
        }

        Funcionario gestor = null;
        if (funcionarioDTO.getGestorId() != null) {
            gestor = funcionarioRepository.findById(funcionarioDTO.getGestorId())
                    .orElseThrow(() -> new RuntimeException("Gestor não encontrado"));
        }

        funcionario.setMatricula(funcionarioDTO.getMatricula());
        funcionario.setNomeCompleto(funcionarioDTO.getNomeCompleto());
        funcionario.setCpf(funcionarioDTO.getCpf());
        funcionario.setEmail(funcionarioDTO.getEmail());
        funcionario.setTelefone(funcionarioDTO.getTelefone());
        funcionario.setEmpresa(empresa);
        funcionario.setDepartamento(departamento);
        funcionario.setCargo(cargo);
        funcionario.setGestor(gestor);
        funcionario.setStatus(funcionarioDTO.getStatus());
        funcionario.setDataAdmissao(funcionarioDTO.getDataAdmissao());
        funcionario.setDataDemissao(funcionarioDTO.getDataDemissao());
        funcionario.setSalarioAtual(funcionarioDTO.getSalarioAtual());
        funcionario.setDadosFuncionario(funcionarioDTO.getDadosFuncionario());
        funcionario.setPermissoesFuncionario(funcionarioDTO.getPermissoesFuncionario());
        funcionario.setDataAtualizacao(LocalDateTime.now());

        return funcionarioRepository.save(funcionario);
    }

    public void excluirFuncionario(Long id) {
        Funcionario funcionario = buscarFuncionarioPorId(id);
        funcionario.setStatus(Funcionario.StatusFuncionario.INATIVO);
        funcionario.setDataDemissao(LocalDate.now());
        funcionario.setDataAtualizacao(LocalDateTime.now());
        funcionarioRepository.save(funcionario);
    }

    public Funcionario ativarFuncionario(Long id) {
        Funcionario funcionario = buscarFuncionarioPorId(id);
        funcionario.setStatus(Funcionario.StatusFuncionario.ATIVO);
        funcionario.setDataAtualizacao(LocalDateTime.now());
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario desativarFuncionario(Long id) {
        Funcionario funcionario = buscarFuncionarioPorId(id);
        funcionario.setStatus(Funcionario.StatusFuncionario.INATIVO);
        funcionario.setDataAtualizacao(LocalDateTime.now());
        return funcionarioRepository.save(funcionario);
    }
}
