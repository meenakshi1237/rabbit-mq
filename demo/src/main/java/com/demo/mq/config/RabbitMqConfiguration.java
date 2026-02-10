package com.demo.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {
    @Value("${mq.message.queue}")
    private String firstQueue;

    @Value("${mq.message.exchange}")
    private String firstExchange;

    @Value("${mq.message.key}")
    private String firstExchangeKey;

    @Value("${mq.payment.queue}")
    private String paymentQueue;

    @Value("${mq.payment.exchange}")
    private String paymentExchange;

    @Value("${mq.payment.key}")
    private String payentExchangeKey;

    @Value("${mq.event.queue}")
    private String eventQueue;

    @Value("${mq.event.exchange}")
    private String eventExchange;

    @Value("${mq.event.key}")
    private String eventExchangeKey;

    @Value("${mq.notification.queue}")
    private String notificationQueue;

    @Value("${mq.notification.exchange}")
    private String notificationExchange;

    @Value("${mq.notification.key}")
    private String notificationKey;

    @Bean
    public Queue queue(){
        return new Queue(firstQueue);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(firstExchange);
    }

    @Bean
    public Queue payMentQueue(){
        return new Queue(paymentQueue);
    }

    @Bean
    public TopicExchange paymentExchange(){
        return new TopicExchange(paymentExchange);
    }

    @Bean
    public Binding paymentBinding(){
        return BindingBuilder.bind(payMentQueue())
                .to(paymentExchange())
                .with(payentExchangeKey);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(firstExchangeKey);
    }

    @Bean
    public Queue eventQueue(){
        return new Queue(eventQueue);
    }

    @Bean
    public TopicExchange eventExchange(){
        return new TopicExchange(eventExchange);
    }

    @Bean
    public Binding eventExchangeBinding(){
        return BindingBuilder
                .bind(eventQueue())
                .to(eventExchange())
                .with(eventExchangeKey);
    }

    //incase if we send objects as message for the queue in that case we have to add converter for the rabitTemplate

    @Bean
    public MessageConverter converter(){
        //Jakson2JsonMessageConverter is deprecated and removed in spring 4
        return new JacksonJsonMessageConverter();
    }

    //optional
//    @Bean
//    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
//        RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(converter());
//        return rabbitTemplate;
//    }


    @Bean
    public Queue notificationQueue(){
        // if you want normal queue we can do like this
//        return new Queue(notificationQueue);
        // if we need queue with the
        return QueueBuilder
                .durable(notificationQueue)
                .withArgument("x-dead-letter-exchange", "notification.dxl.exchange")
                .withArgument("x-dead-letter-routing-key", "notification.dql.queue")
                .build();
    }

    @Bean
    public TopicExchange notificationExchange(){
        return new TopicExchange(notificationExchange);
    }

    @Bean
    public Binding notificationBinding(){
        return  BindingBuilder.
                bind(notificationQueue())
                .to(notificationExchange())
                .with(notificationKey);

    }

    //-----Notification DLQ queue-----
    @Bean
    public Queue notificationDLQQueue(){
        return new Queue("notification.dql.queue");
    }

    //----Notification DLX exchange
    @Bean
    public DirectExchange notificationDLXExchange(){
        return new DirectExchange("notification.dxl.exchange");
    }

    @Bean
    public Binding notificationBLQBinding(){
        return BindingBuilder
                .bind(notificationDLQQueue())
                .to(notificationDLXExchange())
                .with("notification.dlq.key");
    }
}
