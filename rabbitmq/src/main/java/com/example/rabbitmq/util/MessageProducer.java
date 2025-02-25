package com.example.rabbitmq.util;

import com.example.rabbitmq.config.Lead;
import com.example.rabbitmq.config.RabbitMqConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(String message){
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME,RabbitMqConfig.ROUTING_KEY,message);
        System.out.println("message sent to quque: "+message);
    }

    public void sendObject(Lead lead,String routingKey){
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME,routingKey,lead);
    }
}
