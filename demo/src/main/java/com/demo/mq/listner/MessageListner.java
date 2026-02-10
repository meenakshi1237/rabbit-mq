package com.demo.mq.listner;

import com.demo.mq.dto.Event;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MessageListner {

    @RabbitListener(queues = {"first.queue"})
    public void messageListner(String message){
        System.out.println("recived message is: " + message);
    }

    @RabbitListener(queues = {"payment.queue"})
    public void paymentListner(String amount){
        System.out.println("amount recived: "+amount);
    }

    @RabbitListener(queues = {"event.queue"})
    public void  eventListner(Event event){
        log.info("event recived:{}",event.getId());
    }

    @RabbitListener(queues = {"notification.queue"})
    public void notificationListner(String messsage, Channel channel, Message message) throws IOException {
        long deliveryTag=message.getMessageProperties().getDeliveryTag();
        try{
            log.info("notification listened :{}",messsage);
            channel.basicAck(deliveryTag, false);
//            throw new RuntimeException("failed");
            //basicAck(tag,multiple)
        }catch(Exception _){ //instead of defining a variable use _ since variable is not used anywhere java 25
            // permanent failure → DLQ
            channel.basicNack(deliveryTag,false,true);
//            basicNack(tag,multiple,require)
            //require means put message back to queue
        }
    }
}
