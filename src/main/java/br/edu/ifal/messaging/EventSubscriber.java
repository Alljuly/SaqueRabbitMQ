package br.edu.ifal.messaging;

public interface EventSubscriber {
    void inscrever(String fila, MessageHandler handler) throws Exception;

    @FunctionalInterface
    interface MessageHandler {
        void processar(String mensagem);
    }
}
