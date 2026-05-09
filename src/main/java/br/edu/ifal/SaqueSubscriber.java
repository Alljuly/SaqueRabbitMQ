package br.edu.ifal;

import br.edu.ifal.messaging.EventSubscriber;
import br.edu.ifal.messaging.RabbitMQSubscriber;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SaqueSubscriber {

    private static final String QUEUE_NAME = "send-emails";

    public static void main(String[] args) throws Exception {
        EventSubscriber subscriber = new RabbitMQSubscriber("localhost", "guest", "guest");

        System.out.println("Aguardando mensagens na fila '" + QUEUE_NAME + "'...");

        subscriber.inscrever(QUEUE_NAME, mensagem -> {
            JsonObject evento = JsonParser.parseString(mensagem).getAsJsonObject();

            System.out.println("\n========== SIMULAÇÃO DE E-MAIL ==========");
            System.out.println("Para: " + evento.get("email").getAsString());
            System.out.println("Assunto: Notificação de Saque");
            System.out.println("-----------------------------------------");
            System.out.println("Olá, " + evento.get("titular").getAsString() + "!");
            System.out.println("Um saque de R$ " + evento.get("valorSaque").getAsDouble()
                    + " foi realizado em sua conta.");
            System.out.println("Saldo restante: R$ " + evento.get("saldoRestante").getAsDouble());
            System.out.println("Data/Hora: " + evento.get("dataHora").getAsString());
            System.out.println("=========================================\n");
        });
    }
}
