package com.example.bankapicode.controller;

import com.example.bankapicode.model.ClienteData;
import com.example.bankapicode.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> findByCpf(@PathVariable String cpf) {
        try {
            return ResponseEntity.ok().body(clienteService.findByCpf(cpf).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ClienteData clienteData) {
        try{
            clienteService.save(clienteData);
            return ResponseEntity.ok().body(clienteData.toString());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("/update/{cpf}")
    public ResponseEntity<String> updateByCpf(@PathVariable String cpf, @RequestBody ClienteData newClienteData) {
        try{
            clienteService.updateByCpf(cpf, newClienteData);
            return ResponseEntity.ok().body(newClienteData.toString());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/delete/{cpf}")
    public void deleteByCpf(@PathVariable String cpf) {
        clienteService.deleteByCpf(cpf);
    }
    @GetMapping("/findAllByNome/{nome}")
    public List<ClienteData> findAllByNome(@PathVariable String nome) {
        return clienteService.findAllByNome(nome);
    }
    @GetMapping("/findAllByEmail/{email}")
    public List<ClienteData> findAllByEmail(@PathVariable String email) {
        return clienteService.findAllByEmail(email);
    }
    @GetMapping("/findAllByTelefone/{telefone}")
    public List<ClienteData> findAllByTelefone(@PathVariable String telefone) {
        return clienteService.findAllByTelefone(telefone);
    }
}
