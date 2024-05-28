package com.example.bankapicode.service;

import com.example.bankapicode.model.ClienteData;
import com.example.bankapicode.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    public List<ClienteData> findAll() { return clienteRepository.findAll(); }
    public ClienteData findByCpf(String cpf) { return clienteRepository.findById(cpf).orElse(null); }
    public void save(ClienteData clienteData) { clienteRepository.save(clienteData); }
    public void delete(ClienteData clienteData) { clienteRepository.delete(clienteData); }
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
    public Optional<ClienteData> deleteByCpf(String cpf) {
        Optional<ClienteData> clienteData = clienteRepository.findById(cpf);
        if (clienteData.isPresent()) {
            clienteRepository.delete(clienteData.get());
            return Optional.of(clienteData.get());
        } else {
            return Optional.empty();
        }
    }
}
