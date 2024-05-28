package com.example.bankapicode.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "conta")
public class ContaData {
    @Id
    @Size(max = 5, min = 5 , message = "Numero da conta must be 5 digits")
    private String numero_conta;
    private Double saldo;
    @Min(value = 0, message = "Limite especial must be greater than 0")
    private Double limite_especial;
    @Size(max = 11, min = 11 , message = "Cpf must be 11 digits")
    private String cliente_cpf;

    public ContaData() {
        this.numero_conta = "";
        this.saldo = 0.0;
        this.limite_especial = 0.0;
        this.cliente_cpf = "";
    }
    public ContaData(String numero_conta, Double saldo, Double limite_especial, String cliente_cpf) {
        this.saldo = saldo;
        this.limite_especial = limite_especial;
        this.cliente_cpf = cliente_cpf;
    }
    public String getNumero_conta() {
        return numero_conta;
    }
    public void setNumero_conta(String numero_conta) {
        this.numero_conta = numero_conta;
    }
    public Double getSaldo() {
        return saldo;
    }
    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
    public Double getLimite_especial() {
        return limite_especial;
    }
    public void setLimite_especial(Double limite_especial) {
        this.limite_especial = limite_especial;
    }
    public String getCliente_cpf() {
        return cliente_cpf;
    }
    public void setCliente_cpf(String cliente_cpf) {
        this.cliente_cpf = cliente_cpf;
    }

    @Override
    public String toString(){
        return "ContaData{" +
                "numero_conta='" + numero_conta + '\'' +
                ", saldo=" + saldo +
                ", limite_especial=" + limite_especial +
                ", cliente_cpf='" + cliente_cpf + '\'' +
                '}';
    }
}
