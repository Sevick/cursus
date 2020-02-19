package com.fbytes.cursus.services.sender.transport;

public interface IRabbitMQSender {
    void send(String exchange, String routingkey, String message);
}
