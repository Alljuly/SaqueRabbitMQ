package br.edu.ifal;

import br.edu.ifal.messaging.EventPublisher;
import br.edu.ifal.messaging.RabbitMQPublisher;
import br.edu.ifal.model.Conta;
import br.edu.ifal.model.ContaCorrente;
import com.google.gson.JsonObject;

import java.time.LocalDateTime;

public class SaquePublisher {

    private static final String QUEUE_NAME = "send-emails";

    public static void main(String[] args) throws Exception {
        Conta conta = new ContaCorrente("João Silva", "joao@email.com", 1000.00);
        double valorSaque = 250.00;

        System.out.println("=== OPERAÇÃO DE SAQUE ===");
        System.out.println("Titular: " + conta.getTitular());
        System.out.println("Saldo atual: R$ " + conta.getSaldo());
        System.out.println("Valor do saque: R$ " + valorSaque);

        if (!conta.sacar(valorSaque)) {
            System.out.println("ERRO: Saldo insuficiente ou valor inválido!");
            return;
        }

        System.out.println("Saque realizado com sucesso! Novo saldo: R$ " + conta.getSaldo());

        JsonObject evento = new JsonObject();
        evento.addProperty("titular", conta.getTitular());
        evento.addProperty("email", conta.getEmail());
        evento.addProperty("valorSaque", valorSaque);
        evento.addProperty("saldoRestante", conta.getSaldo());
        evento.addProperty("dataHora", LocalDateTime.now().toString());

        EventPublisher publisher = new RabbitMQPublisher("localhost", "guest", "guest");
        try {
            publisher.publicar(QUEUE_NAME, evento.toString());
            System.out.println("Evento de saque publicado na fila '" + QUEUE_NAME + "'");
        } finally {
            publisher.fechar();
        }
    }
}
