package br.edu.ifal.messaging;

import com.rabbitmq.client.*;

import java.io.IOException;

public class RabbitMQSubscriber implements EventSubscriber {
    private final Connection connection;
    private final Channel channel;

    public RabbitMQSubscriber(String host, String username, String password) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setUsername(username);
        factory.setPassword(password);
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();
    }

    @Override
    public void inscrever(String fila, MessageHandler handler) throws Exception {
        channel.queueDeclare(fila, false, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                handler.processar(new String(body, "UTF-8"));
            }
        };

        channel.basicConsume(fila, true, consumer);
    }
}
