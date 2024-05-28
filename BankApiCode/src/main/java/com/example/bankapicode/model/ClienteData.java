package com.example.bankapicode.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cliente")
public class ClienteData {
    @Id
    @NotNull(message = "Cpf cannot be null")
    @Size(max = 11, min = 11 , message = "Cpf must be 11 digits")
    private String cpf;
    @NotNull(message = "Name cannot be null")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String nome;
    @NotNull(message = "Email cannot be null")
    @Size(max = 255, message = "Email must be less than 255 characters")
    private String email;
    @NotNull(message = "Telefone cannot be null")
    @Size(max = 15, message = "Telefone must be less than 15 characters")
    @Size(min = 8, message = "Telefone must be greater than 8 characters")
    private String telefone;

    public ClienteData() {
        this.cpf = "";
        this.nome = "";
        this.email = "";
        this.telefone = "";
    }
    public ClienteData(String cpf, String nome, String email, String telefone) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    @Override
    public String toString(){
        return "ProdutoData{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
