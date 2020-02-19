package com.fbytes.cursus.services.sender;

import com.fbytes.cursus.services.sender.transport.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitGoogleAction implements ISendInterface {

    @Value("${cursus.gassist2rabbit.exchange}")
    private String exchange;
    @Value("${cursus.gassist2rabbit.routingkey}")
    private String routingkey;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Override
    public void sendData(String message) {
        rabbitMQSender.send(exchange, routingkey, message);
    }

    @Override
    public String toString() {
        return this.getClass().toString();
    }
}

