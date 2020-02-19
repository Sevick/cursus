package com.fbytes.cursus.services.sender.transport;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender implements IRabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Override
    public void send(String exchange, String routingkey, String message) {
        rabbitTemplate.convertAndSend(exchange, routingkey, message);
    }
}