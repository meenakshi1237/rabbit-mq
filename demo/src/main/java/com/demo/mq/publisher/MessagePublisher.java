package com.demo.mq.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {

    @Value("${mq.message.exchange}")
    private String firstExchange;

    @Value("${mq.message.key}")
    private String firstExchangeKey;

    @Value("${mq.payment.exchange}")
    private String paymentExchange;

    @Value("${mq.payment.key}")
    private String payentExchangeKey;

    private final RabbitTemplate rabbitTemplate;

    public MessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishMessage(String message){
        rabbitTemplate.convertAndSend(firstExchange,firstExchangeKey,message);
    }

    public void amountPublisher(String amount){
        rabbitTemplate.convertAndSend(paymentExchange,payentExchangeKey,amount);
    }
}
