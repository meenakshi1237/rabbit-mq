package com.demo.mq.controller;

import com.demo.mq.dto.Event;
import com.demo.mq.publisher.EventPublisher;
import com.demo.mq.publisher.MessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessagePublisher messagePublisher;
    private final EventPublisher eventPublisher;

    @GetMapping("/api/v1/publish")
    public String publishMessage(@RequestParam String mesage){
        messagePublisher.publishMessage(mesage);
        return "message published";
    }

    @GetMapping("/api/v1/amount")
    public String publishAmount(@RequestParam String amount){
        messagePublisher.amountPublisher(amount);
        return "message published after payment";
    }

    @PostMapping("/api/v1/event")
    public String publishEvent(@RequestBody Event event){
        return eventPublisher.publishEvent(event);
    }

    @GetMapping("/api/v1/notification")
    public String publishNotificatoions(){
        return eventPublisher.notificationPublisher("your added as team member");
    }

}
