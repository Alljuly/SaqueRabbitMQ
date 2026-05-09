package br.edu.ifal.model;

public class ContaPoupanca extends ContaBancaria {
    private static final double TAXA_RENDIMENTO = 0.005;

    public ContaPoupanca(String titular, String email, double saldo) {
        super(titular, email, saldo);
    }

    @Override
    public boolean sacar(double valor) {
        if (valor <= 0 || valor > saldo) return false;
        saldo -= valor;
        return true;
    }

    public void aplicarRendimento() {
        saldo += saldo * TAXA_RENDIMENTO;
    }
}
