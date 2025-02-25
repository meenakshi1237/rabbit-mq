package com.example.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String STRING_QUEUE_NAME = "queue1";
    public static final String OBJECT_QUEUE_NAME="objectqueue";
    public static final String EXCHANGE_NAME = "exchange1";
    public static final String ROUTING_KEY = "key1";
    public static final String OBJECT_KEY="key2";

    @Bean
    public Queue StringQueue() {
        return new Queue(STRING_QUEUE_NAME, true);
    }

    @Bean
    public Queue objectQueue(){
        return new Queue(OBJECT_QUEUE_NAME,true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(StringQueue()).to(directExchange()).with(ROUTING_KEY);
    }

    @Bean
    public Binding objectBinding(){
        return BindingBuilder.bind(objectQueue()).to(directExchange()).with(OBJECT_KEY);
    }

}
