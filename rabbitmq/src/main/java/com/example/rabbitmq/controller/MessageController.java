package com.example.rabbitmq.controller;

import com.example.rabbitmq.config.Lead;
import com.example.rabbitmq.config.RabbitMqConfig;
import com.example.rabbitmq.util.MessageProducer;
import com.example.rabbitmq.util.MqListner;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageProducer messageProducer;

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        messageProducer.sendMessage(message);

        return "Message sent: " + message;
    }

    @PostMapping("/obj")
    public void sendLead(@RequestBody Lead lead){
        messageProducer.sendObject(lead, RabbitMqConfig.OBJECT_KEY);
        System.out.println("message sent");

    }

    @RabbitListener(queues = RabbitMqConfig.STRING_QUEUE_NAME)
    public void reciveMessage(String message){
        System.err.println("consumed String: "+message);
    }


}
