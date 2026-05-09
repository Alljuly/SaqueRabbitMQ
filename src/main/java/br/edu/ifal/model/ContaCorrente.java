package br.edu.ifal.model;

public class ContaCorrente extends ContaBancaria {

    public ContaCorrente(String titular, String email, double saldo) {
        super(titular, email, saldo);
    }

    @Override
    public boolean sacar(double valor) {
        if (valor <= 0 || valor > saldo) return false;
        saldo -= valor;
        return true;
    }
}
