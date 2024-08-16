package com.example.bankapicode.service;

import com.example.bankapicode.model.ClienteData;
import com.example.bankapicode.model.ContaData;
import com.example.bankapicode.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    private ContaService contaService;

    public void setContaService(ContaService contaService) {
        this.contaService = contaService;
    }

    public List<ClienteData> findAll() { return clienteRepository.findAll(); }
    public ClienteData findByCpf(String cpf) { return clienteRepository.findById(cpf).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado")); }
    public void save(ClienteData clienteData) { clienteRepository.save(clienteData); }
    public Optional<ClienteData> updateByCpf(String cpf, ClienteData newClienteData) {
        Optional<ClienteData> clienteData = clienteRepository.findById(cpf);
        if (clienteData.isPresent()) {
            ClienteData oldCliente = clienteData.get();
            oldCliente.setNome(newClienteData.getNome());
            oldCliente.setEmail(newClienteData.getEmail());
            oldCliente.setTelefone(newClienteData.getTelefone());
            clienteRepository.save(oldCliente);
            return Optional.of(oldCliente);
        } else {
            return Optional.empty();
        }
    }
    @Transactional
    public Optional<ClienteData> deleteByCpf(String cpf) {
        Optional<ClienteData> clienteData = clienteRepository.findById(cpf);
        if (clienteData.isPresent()) {
            List<ContaData> contas = contaService.findAllByCliente_cpf(cpf);
            if(contas.size()>0){
                throw new IllegalArgumentException("O cliente possui contas associadas");
            }else {
                clienteRepository.delete(clienteData.get());
                return Optional.of(clienteData.get());
            }
        } else {
            return Optional.empty();
        }
    }
    //Métodos de pesquisa variado
    public List<ClienteData> findAllByNome(String nome) {
        return clienteRepository.findAllByNome(nome);
    }

    public List<ClienteData> findAllByEmail(String email) {
        return clienteRepository.findAllByEmail(email);
    }

    public List<ClienteData> findAllByTelefone(String telefone) {
        return clienteRepository.findAllByTelefone(telefone);
    }
}