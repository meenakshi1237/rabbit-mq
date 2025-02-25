package com.example.rabbitmq.util;

import com.example.rabbitmq.config.Lead;
import com.example.rabbitmq.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Component
public class MqListner {
    @RabbitListener(queues = RabbitMqConfig.STRING_QUEUE_NAME)
    public void reciveMessage(String message){
        System.err.println("consumed String: "+message);
    }

    @RabbitListener(queues = RabbitMqConfig.OBJECT_QUEUE_NAME)
    public void recieveObject(Lead lead){
        System.err.println(lead);
    }
}
