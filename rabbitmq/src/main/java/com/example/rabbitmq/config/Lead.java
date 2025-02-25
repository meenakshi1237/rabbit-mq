package com.example.rabbitmq.config;

import lombok.Data;

import java.io.Serializable;

@Data
public class Lead implements Serializable {
    private String name;
    private String email;
}
