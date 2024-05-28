package com.example.bankapicode.controller;

import com.example.bankapicode.model.ContaData;
import com.example.bankapicode.model.TransferenciaResultado;
import com.example.bankapicode.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contas")
public class ContaController {
    private final ContaService contaService;
    @Autowired
    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @GetMapping("/findAll")
    public List<ContaData> findAll() {
        return contaService.findAll();
    }

    @GetMapping("/find/{numero_conta}")
    public ContaData findByNumero_conta(String numero_conta) {
        return contaService.findByNumero_conta(numero_conta);
    }

    @PostMapping("/save")
    public void save(@Valid @RequestBody ContaData contaData) {
        contaService.save(contaData);
    }

    @PutMapping("/update")
    public void updateByNumero_conta(@RequestBody String numero_conta, @RequestBody ContaData newContaData) {
        contaService.updateByNumero_conta(numero_conta, newContaData);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody ContaData contaData) {
        contaService.delete(contaData);
    }
    @PutMapping("/depositar")
    public void depositar(@RequestBody String numero_conta, @RequestBody Double valor) {
        contaService.depositar(numero_conta, valor);
    }

    @PutMapping("/sacar")
    public void sacar(@RequestBody String numero_conta, @RequestBody Double valor) {
        contaService.sacar(numero_conta, valor);
    }
    @PutMapping("/transferir")
    public TransferenciaResultado transferir(String tipo, String contaOrigem, String contaDestino, Double valor) {
        Optional<TransferenciaResultado> transferencia = contaService.transfer(tipo,contaOrigem, contaDestino, valor);
        if (transferencia.isPresent()) {
            return transferencia.get();
        } else {
            return null;
        }
    }
}
