package com.example.bankapicode.controller;

import com.example.bankapicode.model.ClienteData;
import com.example.bankapicode.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.validation.Validator;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService clienteService;
    private Validator validator;
    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    @GetMapping("/findAll")
    public List<ClienteData> findAll() {
        return clienteService.findAll();
    }
    @GetMapping("/find/{cpf}")
    public ClienteData findByCpf(String cpf) {
        return clienteService.findByCpf(cpf);
    }
    @PostMapping("/save")
    public void save(ClienteData clienteData) {
        clienteService.save(clienteData);
    }
    @PutMapping("/update")
    public void updateByCpf(String cpf, ClienteData newClienteData) {
        clienteService.updateByCpf(cpf, newClienteData);
    }
    @DeleteMapping("/delete")
    public void delete(ClienteData clienteData) {
        clienteService.delete(clienteData);
    }

}
