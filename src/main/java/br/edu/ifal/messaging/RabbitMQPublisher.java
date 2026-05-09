package br.edu.ifal.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQPublisher implements EventPublisher {
    private final Connection connection;
    private final Channel channel;

    public RabbitMQPublisher(String host, String username, String password) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setUsername(username);
        factory.setPassword(password);
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();
    }

    @Override
    public void publicar(String fila, String mensagem) throws Exception {
        channel.queueDeclare(fila, false, false, false, null);
        channel.basicPublish("", fila, null, mensagem.getBytes());
    }

    @Override
    public void fechar() throws Exception {
        channel.close();
        connection.close();
    }
}
