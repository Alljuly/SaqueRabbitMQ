package br.edu.ifal.model;

public interface Conta {
    boolean sacar(double valor);
    boolean depositar(double valor);
    String getTitular();
    String getEmail();
    double getSaldo();
}
