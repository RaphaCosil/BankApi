package com.example.bankapicode.model;

import java.time.LocalDateTime;

public class TransacaoResultado {
    private Double valor;
    private String cpfEmissor;
    private LocalDateTime data;

    public TransacaoResultado() {
        this.valor = 0.0;
        this.cpfEmissor = "";
        this.data = LocalDateTime.now();
    }

    public TransacaoResultado(Double valor, String cpfEmissor, LocalDateTime data) {
        this.valor = valor;
        this.cpfEmissor = cpfEmissor;
        this.data = data;
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

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultadoTransferencia{" +
                "valor=" + valor +
                ", cpfEmissor='" + cpfEmissor + '\'' +
                ", data=" + data +
                '}';
    }

}
