package com.example.bankapicode.model;

import java.time.LocalDateTime;

public class TransferenciaResultado {
    private String tipo;
    private Double valor;
    private String cpfEmissor;
    private String cpfReceptor;
    private LocalDateTime data;

    public TransferenciaResultado() {
        this.tipo = "";
        this.valor = 0.0;
        this.cpfEmissor = "";
        this.cpfReceptor = "";
        this.data = LocalDateTime.now();
    }

    public TransferenciaResultado(String tipo, Double valor, String cpfEmissor, String cpfReceptor, LocalDateTime data) {
        this.tipo = tipo;
        this.valor = valor;
        this.cpfEmissor = cpfEmissor;
        this.cpfReceptor = cpfReceptor;
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getCpfEmissor() {
        return cpfEmissor;
    }

    public void setCpfEmissor(String cpfEmissor) {
        this.cpfEmissor = cpfEmissor;
    }

    public String getCpfReceptor() {
        return cpfReceptor;
    }

    public void setCpfReceptor(String cpfReceptor) {
        this.cpfReceptor = cpfReceptor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultadoTransacao{" +
                "tipo='" + tipo + '\'' +
                ", valor=" + valor +
                ", cpfEmissor='" + cpfEmissor + '\'' +
                ", cpfReceptor='" + cpfReceptor + '\'' +
                ", data=" + data +
                '}';
    }

}
