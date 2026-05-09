package br.edu.ifal.model;

public abstract class ContaBancaria implements Conta {
    protected String titular;
    protected String email;
    protected double saldo;

    public ContaBancaria(String titular, String email, double saldo) {
        this.titular = titular;
        this.email = email;
        this.saldo = saldo;
    }

    @Override
    public boolean depositar(double valor) {
        if (valor <= 0) return false;
        saldo += valor;
        return true;
    }

    @Override
    public String getTitular() { return titular; }

    @Override
    public String getEmail() { return email; }

    @Override
    public double getSaldo() { return saldo; }
}
