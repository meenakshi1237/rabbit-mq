package com.demo.mq.publisher;

import com.demo.mq.dto.Event;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {

    @Value("${mq.event.exchange}")
    private String eventExchange;

    @Value("${mq.event.key}")
    private String eventExchangeKey;

    @Value("${mq.notification.exchange}")
    private String notificationExchange;

    @Value("${mq.notification.key}")
    private String notificationKey;

    private final RabbitTemplate rabbitTemplate;


    public EventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String publishEvent(Event event){
        rabbitTemplate.convertAndSend(eventExchange,eventExchangeKey,event);
        return "event details published successfully";
    }

    public String notificationPublisher(String notification){
        rabbitTemplate.convertAndSend(notificationExchange,notificationKey,notification);
        return "notification sent to user successfully";
    }
}
