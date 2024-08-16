package com.example.bankapicode.controller;

import com.example.bankapicode.model.ContaData;
import com.example.bankapicode.model.TransacaoResultado;
import com.example.bankapicode.model.TransferenciaResultado;
import com.example.bankapicode.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
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
    public ContaData findByNumero_conta(@PathVariable String numero_conta) {
        return contaService.findByNumero_conta(numero_conta);
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@Valid @RequestBody ContaData contaData, BindingResult result) {
        if(result.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.toString());
        }
        return  ResponseEntity.status(HttpStatus.CREATED).body(
                contaService.save(contaData).toString());
    }


    @PutMapping("/update/{numero_conta}")
    public ResponseEntity<String> updateByNumero_conta(@PathVariable String numero_conta, @Valid @RequestBody ContaData newContaData, BindingResult result) {
        if(result.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.toString());
        }
        return ResponseEntity.ok().body(
                contaService.updateByNumero_conta(numero_conta, newContaData)
                        .toString());
    }

    @DeleteMapping("/delete/{numero_conta}")
    public ResponseEntity<String> deleteByNumero_conta(@PathVariable String numero_conta, BindingResult result) {
        if(result.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.toString());
        }
        return ResponseEntity.ok().body(
        contaService.deleteByNumero_conta(numero_conta).toString());
    }

    @PutMapping("/depositar/{numero_conta}/{valor}")
    public ResponseEntity<String> depositar(@PathVariable String numero_conta, @PathVariable Double valor, BindingResult result) {
        if(result.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.toString());
        }else {
            Optional<TransacaoResultado> transacao = contaService.depositar(numero_conta, valor);
            if (transacao.isPresent()) {
                return ResponseEntity.ok(transacao.get().toString());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possivel realizar a operação");
            }
        }
    }

    @PutMapping("/sacar/{numero_conta}/{valor}")
    public ResponseEntity<String> sacar(@PathVariable String numero_conta, @PathVariable Double valor, BindingResult result) {
        if(result.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.toString());
        }else {
            try {
                Optional<TransacaoResultado> transacao = contaService.sacar(numero_conta, valor);
                if (transacao.isPresent()) {
                    return ResponseEntity.ok(transacao.get().toString());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Não foi possivel realizar a operação");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(e.getMessage());
            }
        }
    }
    @PutMapping("/transferir/{tipo}/{contaOrigem}/{contaDestino}/{valor}")
    public ResponseEntity transferir(@PathVariable String tipo, @PathVariable String contaOrigem,
                                             @PathVariable String contaDestino, @PathVariable Double valor, BindingResult result) {
        if(result.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.toString());
        }else {
            try {
                Optional<TransferenciaResultado> transferencia = contaService.transfer(tipo, contaOrigem, contaDestino, valor);
                if (transferencia.isPresent()) {
                    return ResponseEntity.ok(transferencia.get().toString());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possivel realizar a operação");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
    }
    //Métodos de consulta derivados
    @GetMapping("/findAllBySaldoIsOrderBySaldoDesc/{saldo}")
    public List<ContaData> findAllBySaldoIsOrderBySaldoDesc(@PathVariable Double saldo) {
        return contaService.findAllBySaldoIsLessThan(saldo);
    }

    @GetMapping("/findAllOrderBySaldo/{saldo}")
    public List<ContaData> findAllOrderBySaldo(@PathVariable Double saldo) {
        return contaService.findAllOrderBySaldo(saldo);
    }

    @GetMapping("/findAllBySaldoNull")
    public List<ContaData> findAllByCliente_cpf() {
        return contaService.findAllBySaldoNull();
    }

    private ResponseEntity<String> getStringResponseEntity(BindingResult result) {
        int quantityOfError = result.getErrorCount();
        StringBuilder error = new StringBuilder();
        if(quantityOfError>1) {
            for(int i = 0; i < quantityOfError-1; i++) {
                error.append(result.getAllErrors().get(i).getDefaultMessage()).append(" | ");
            }
            error.append(result.getAllErrors().get(quantityOfError-1).getDefaultMessage());
        }
        else {
            error.append(result.getAllErrors().get(0).getDefaultMessage());
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error.toString());
    }
}
