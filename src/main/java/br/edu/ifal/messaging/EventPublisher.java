package br.edu.ifal.messaging;

public interface EventPublisher {
    void publicar(String fila, String mensagem) throws Exception;
    void fechar() throws Exception;
}
